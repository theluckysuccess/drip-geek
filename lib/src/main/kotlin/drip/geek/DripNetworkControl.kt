package drip.geek

import drip.geek.DisplayManager.Companion.toScale
import io.github.cdimascio.dotenv.dotenv
import java.math.BigDecimal
import java.math.MathContext
import mu.KLogging

class DripNetworkControl(private val wallets: List<DripWallet>) {

    private fun calcTimeLeft(depositAmount: BigDecimal, availableBalance: BigDecimal): BigDecimal {
        val amount = depositAmount.multiply(BigDecimal(".01"))
        val missingDrip = amount - availableBalance
        val dripPerMinute = amount.divide(BigDecimal(86400), MathContext.DECIMAL128)
        return missingDrip.divide(dripPerMinute, MathContext.DECIMAL128)
    }

    private fun minAvailableBalanceToHydrateByDepositAmount(depositAmount: BigDecimal): BigDecimal =
        when {
            depositAmount < BigDecimal("3") -> depositAmount.multiply(BigDecimal(".03"))
            depositAmount < BigDecimal("4") -> depositAmount.multiply(BigDecimal(".25"))
            depositAmount < BigDecimal("5") -> depositAmount.multiply(BigDecimal(".02"))
            else -> depositAmount.multiply(BigDecimal(".01"))
        }

    private fun getFundableFaucetWalletToBNBBalanceListSorted(): List<Pair<Faucet, BigDecimal>> {
        return getFaucetWalletToBNBBalanceListSorted { it.fundable }
    }

    private fun getFaucetWalletToBNBBalanceListSorted(
        predicate: (DripWallet) -> Boolean = { true }
    ): List<Pair<Faucet, BigDecimal>> =
        Web3Client.init().let { web3Client ->
            wallets.filter(predicate).map { wallet ->
                Faucet.build(wallet, web3Client).let { faucet -> faucet to faucet.bnbBalance() }
            }.sortedBy { it.second }.also { web3Client.close() }
        }

    private fun attemptToFundWallets(
        amountToFund: BigDecimal,
        mainWalletFaucet: Faucet,
        balanceOfMainBnbWallet: BigDecimal,
        lowerFaucetToFaucetBNBBalance: Pair<Faucet, BigDecimal>?,
        walletsToFund: List<Faucet>,
        totalToFund: BigDecimal
    ) = when {
        walletsToFund.isEmpty() -> {
            logger.info { "process=$PROCESS_NAME | No wallets found that need BNB funds" }
            lowerFaucetToFaucetBNBBalance?.let { (walletName, walletBnbBalance) ->
                logger.info { "process=$PROCESS_NAME | [$walletName] has the lowest balance [$walletBnbBalance]" }
            }
        }

        balanceOfMainBnbWallet > totalToFund -> walletsToFund
            .filter { it.address.value != mainWalletFaucet.address.value } // The main wallet may not send to itself
            .forEach {
                logger.info { "process=$PROCESS_NAME | Starting move of $amountToFund BNB from ${mainWalletFaucet.name} to ${it.name} [${it.address}]" }
                mainWalletFaucet.sendBNBFunds(to = it.address, amount = amountToFund)
                logger.info { "process=$PROCESS_NAME | Successfully moved $amountToFund BNB from ${mainWalletFaucet.name} to ${it.name} [${it.address}]" }
            }
            .also { logger.info { "process=$PROCESS_NAME | Completed moved of $amountToFund BNB to all fundable wallets" } }

        else -> {
            logger.warn { "process=$PROCESS_NAME | Insufficient funds" }
            logger.warn { "process=$PROCESS_NAME | Needed to move a total of $totalToFund BNB to ${walletsToFund.size} wallet(s)" }
            logger.warn { "process=$PROCESS_NAME | Main wallet [${DripWallets.MAIN_BNB_WALLET.name}] only had [$balanceOfMainBnbWallet]" }
        }
    }

    fun walletsReport() {
        val dripPrice = RestClient.currentDripPrice()
        val bnbPrice = RestClient.currentBNBPrice()
        val displayManager = DisplayManager.build(dripPrice, bnbPrice)

        logger.info { "process=$PROCESS_NAME | Current price of drip is: $$dripPrice" }
        val web3Client = Web3Client.init()
        wallets.sortedBy { it.name }.forEach {
            val faucet = Faucet.build(it, web3Client)
            val walletStats = faucet.walletStats(dripPrice, it.name)
            val minAvailableBalanceToHydrate = minAvailableBalanceToHydrateByDepositAmount(walletStats.depositBalance)
            displayManager.displayWalletReport(PROCESS_NAME, walletStats, minAvailableBalanceToHydrate, MIN_BNB_BALANCE)
        }
        displayManager.displayWalletReportSummary(PROCESS_NAME)
        web3Client.close()
    }

    fun walletsBNBReport() {
        val dripPrice = RestClient.currentDripPrice()
        val bnbPrice = RestClient.currentBNBPrice()
        val displayManager = DisplayManager.build(dripPrice, bnbPrice)

        logger.info { "process=$PROCESS_NAME | Current price of drip is: $$dripPrice" }
        logger.info { "process=$PROCESS_NAME | Current price of BNB is: $${bnbPrice.toScale()}" }

        val web3Client = Web3Client.init()
        displayManager.displayLine()
        getFaucetWalletToBNBBalanceListSorted().forEach { (faucet, bnbBalance) ->
            displayManager.displayWalletBNBReport(PROCESS_NAME, faucet.name, bnbBalance)
        }
        displayManager.displayWalletBNBReportSummary(PROCESS_NAME)
        web3Client.close()
    }

    fun sendBNBToWallets(amount: BigDecimal) {
        val web3Client = Web3Client.init()
        val mainWalletFaucet = Faucet.build(DripWallets.MAIN_BNB_WALLET, web3Client)
        val fundableWalletsSorted = getFundableFaucetWalletToBNBBalanceListSorted()
        val sum = amount.multiply(BigDecimal(fundableWalletsSorted.count()))
        val balanceOfMainBnbWallet = mainWalletFaucet.bnbBalance()

        attemptToFundWallets(
            amountToFund = amount,
            mainWalletFaucet = mainWalletFaucet,
            balanceOfMainBnbWallet = balanceOfMainBnbWallet,
            lowerFaucetToFaucetBNBBalance = fundableWalletsSorted.firstOrNull(),
            walletsToFund = fundableWalletsSorted.map { it.first },
            totalToFund = sum
        )

        web3Client.close()
    }

    fun lowBNBBalanceFindAndFund() {
        val web3Client = Web3Client.init()
        val mainWalletFaucet = Faucet.build(DripWallets.MAIN_BNB_WALLET, web3Client)
        val fundableWalletsSorted = getFundableFaucetWalletToBNBBalanceListSorted()
        val balanceOfMainBnbWallet = mainWalletFaucet.bnbBalance()
        val belowMinBalanceWallets =
            fundableWalletsSorted.filter { (_, walletBnbBalance) -> walletBnbBalance < MIN_BNB_BALANCE }
        val sumOfLowBalanceWallets = belowMinBalanceWallets.sumOf { (_, walletBnbBalance) -> walletBnbBalance }

        attemptToFundWallets(
            amountToFund = FUND_BNB_BALANCE,
            mainWalletFaucet = mainWalletFaucet,
            balanceOfMainBnbWallet = balanceOfMainBnbWallet,
            lowerFaucetToFaucetBNBBalance = fundableWalletsSorted.firstOrNull(),
            walletsToFund = belowMinBalanceWallets.map { it.first },
            totalToFund = sumOfLowBalanceWallets
        )
        web3Client.close()
    }

    fun hydrateWallets(hydrationStyle: HydrationStyle = HydrationStyle.ALL) {
        val dripPrice = RestClient.currentDripPrice()
        val bnbPrice = RestClient.currentBNBPrice()
        val displayManager = DisplayManager.build(dripPrice, bnbPrice)
        displayManager.startHydration(PROCESS_NAME)
        val web3Client = Web3Client.init()

        wallets.sortedBy { it.name }.forEach {
            val faucet = Faucet.build(it, web3Client)
            val walletStats = faucet.walletStats(dripPrice, it.name)
            val minAvailableBalanceToHydrate = minAvailableBalanceToHydrateByDepositAmount(walletStats.depositBalance)
            displayManager.displayWalletStats(PROCESS_NAME, walletStats, minAvailableBalanceToHydrate, MIN_BNB_BALANCE)
            when (hydrationStyle.canHydrate(walletStats.availableBalance, minAvailableBalanceToHydrate)) {
                true -> when (val result = faucet.hydrate()) { // YAY!
                    is TransactionResponse.Error ->
                        logger.error {
                            "process=$PROCESS_NAME | Error processing ${walletStats.name}: ${result.message} | skipping"
                        }
                    is TransactionResponse.Success -> displayManager.displayHydrationInfo(
                        PROCESS_NAME,
                        walletStats,
                        result.transactionHash,
                        faucet.newDepositAmount()
                    )
                }
                else -> displayManager.displaySkipHydration(
                    PROCESS_NAME,
                    walletStats,
                    minAvailableBalanceToHydrate,
                    calcTimeLeft(walletStats.depositBalance, walletStats.availableBalance)
                )
            }
        }
        displayManager.displaySummary(PROCESS_NAME)
        web3Client.close()
    }

    companion object : KLogging() {
        private const val PROCESS_NAME = "drip-network-control"
        private val FUND_BNB_BALANCE = BigDecimal(dotenv()["FUND_BNB_BALANCE"])
        private val MIN_BNB_BALANCE = BigDecimal(dotenv()["MIN_BNB_BALANCE"])
    }
}
