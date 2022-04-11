package drip.geek

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.TimeUnit
import mu.KLogging

class DisplayManager(
    private val dripPrice: BigDecimal,
    private val bnbPrice: BigDecimal,
) {
    var totalDrip: BigDecimal = BigDecimal.ZERO
    var totalDripValue: BigDecimal = BigDecimal.ZERO
    var totalBNB: BigDecimal = BigDecimal.ZERO
    var totalBNBValue: BigDecimal = BigDecimal.ZERO
    var totalHealthyBNBBalanceWallets: BigDecimal = BigDecimal.ZERO
    var totalLowBNBBalanceWallets: BigDecimal = BigDecimal.ZERO
    var totalNeedRefillBNBBalanceWallets: BigDecimal = BigDecimal.ZERO
    private var totalDripCompounded: BigDecimal = BigDecimal.ZERO
    private var totalDripCompoundedValue: BigDecimal = BigDecimal.ZERO

    fun startHydration(processName: String) {
        logger.info { "============================" }
        logger.info { "process=$processName | Starting process: hydrating regular wallets" }
        logger.info { "process=$processName | Current price of drip is: $$dripPrice" }
    }

    fun displayWalletStats(
        processName: String,
        dripWalletStats: DripWalletStats,
        balanceNeededToHydrate: BigDecimal
    ) {
        logger.info { "============================" }
        logger.info { "process=$processName | Wallet Name:             ${dripWalletStats.name}" }
        logger.info { "process=$processName | BNB balance:             ${dripWalletStats.bnbBalance}" }
        logger.info { "process=$processName | BNB balance health:      ${dripWalletStats.bnbBalanceHealth}" }
        logger.info { "process=$processName | Deposit Amount:          ${dripWalletStats.depositBalance}" }
        logger.info { "process=$processName | Deposit Value:           $${dripWalletStats.depositBalanceValue.toScale()}" }
        logger.info { "process=$processName | Claimable amount:        ${dripWalletStats.availableBalance}" }
        logger.info { "process=$processName | Claimed amount:          ${dripWalletStats.claimedBalance}" }
        logger.info { "process=$processName | Balance Need to Hydrate: $balanceNeededToHydrate" }
        logger.info { "============================" }
    }

    fun displayHydrationInfo(
        processName: String,
        walletStats: DripWalletStats,
        transactionHash: String,
        newDepositAmount: BigDecimal
    ) {
        val newDepositValue = newDepositAmount.multiply(dripPrice)
        logger.info { "process=$processName | Hydrated ${walletStats.name}!" }
        logger.info { "process=$processName | Transaction Hash: https://bscscan.com/tx/$transactionHash" }
        logger.info { "process=$processName | Added ${walletStats.availableBalance} to deposit for ${walletStats.name}" }
        logger.info { "process=$processName | Total Drip in faucet for ${walletStats.name} is now $newDepositAmount" }
        logger.info { "process=$processName | Total Drip in faucet for ${walletStats.name} is now worth $${newDepositValue.toScale()}" }
        totalDrip += newDepositAmount
        totalDripValue += newDepositValue
        totalDripCompounded += walletStats.availableBalance
        totalDripCompoundedValue += walletStats.availableBalance.multiply(dripPrice)
    }

    fun displaySkipHydration(
        processName: String,
        walletStats: DripWalletStats,
        balanceNeededToHydrate: BigDecimal,
        timeRemaining: BigDecimal
    ) {
        val difference = balanceNeededToHydrate - walletStats.availableBalance
        logger.info { "process=$processName | Skipping Hydration: ${walletStats.availableBalance} Drip available | need $difference more" }
        logger.info { "process=$processName | Approximately ${TimeUnit.SECONDS.toMinutes(timeRemaining.toLong())} minute(s) left" }
    }

    fun displaySummary(processName: String) {
        displayLine()
        logger.info { "process=$processName | SUMMARY" }
        logger.info { "process=$processName | Total Deposit Amount in Drip: $totalDrip" }
        logger.info { "process=$processName | Total Deposit Value: $${totalDripValue.toScale()}" }
        logger.info { "process=$processName | Total Compounded Amount in Drip: $totalDripCompounded" }
        logger.info { "process=$processName | Total Compounded Value: $${totalDripCompoundedValue.toScale()}" }
        displayLine()
    }

    fun displayWalletReport(
        processName: String,
        walletStats: DripWalletStats,
        minAvailableBalanceToHydrate: BigDecimal
    ) {
        displayWalletStats(processName, walletStats, minAvailableBalanceToHydrate)
        totalDrip += walletStats.depositBalance
        totalDripValue += walletStats.depositBalanceValue
    }

    fun displayWalletReportSummary(processName: String) {
        logger.info { "process=$processName | Total Deposit Amount: $totalDrip" }
        logger.info { "process=$processName | Total Deposit Value:  $${totalDripValue.toScale()}" }
        displayLine()
    }

    fun displayLine() = logger.info { "============================" }

    fun displayWalletBNBReport(
        processName: String,
        walletName: String,
        walletBnbBalance: BigDecimal,
        bnbBalanceHealth: BNBBalanceHealth,
    ) {
        logger.info { "process=$processName | Wallet Name:        $walletName" }
        logger.info { "process=$processName | BNB balance:        $walletBnbBalance" }
        logger.info { "process=$processName | BNB balance health: $bnbBalanceHealth" }
        totalBNB += walletBnbBalance
        totalBNBValue += walletBnbBalance.multiply(bnbPrice)
        when (bnbBalanceHealth) {
            BNBBalanceHealth.HEALTHY -> totalHealthyBNBBalanceWallets++
            BNBBalanceHealth.LOW_BALANCE -> totalLowBNBBalanceWallets++
            BNBBalanceHealth.NEEDS_REFILL -> totalNeedRefillBNBBalanceWallets++
        }
        displayLine()
    }

    fun displayWalletBNBReportSummary(processName: String, fundBNBBalance: BigDecimal, bnbBalance: BigDecimal) {
        logger.info { "process=$processName | Total BNB amount in wallets:              $totalBNB" }
        logger.info { "process=$processName | Total BNB value in wallets:               $${totalBNBValue.toScale()}" }
        logger.info { "process=$processName | Total BNB balance wallets [Healthy]:      $totalHealthyBNBBalanceWallets" }
        logger.info { "process=$processName | Total BNB balance wallets [Low Balance]:  $totalLowBNBBalanceWallets" }
        logger.info { "process=$processName | Total BNB balance wallets [Need Refill]:  $totalNeedRefillBNBBalanceWallets" }
        logger.info { "process=$processName | Current BNB fund amount:                  $fundBNBBalance" }
        logger.info { "process=$processName | Cost to fund each wallet with BNB:        $${fundBNBBalance.multiply(bnbPrice).toScale()}" }
        logger.info { "process=$processName | BNB balance of main wallet:               $bnbBalance" }
        logger.info { "process=$processName | BNB balance value of main wallet:         $${
                bnbBalance.multiply(bnbPrice).toScale()
            }"
        }
        if (totalNeedRefillBNBBalanceWallets != BigDecimal.ZERO) {
            logger.info {
                "process=$processName | Total To Fund Wallets Needing BNB Refill: $${
                    totalNeedRefillBNBBalanceWallets.multiply(bnbPrice).multiply(fundBNBBalance).toScale()
                }"
            }
        }
        displayLine()
    }

    companion object : KLogging() {
        fun BigDecimal.toScale(): BigDecimal = this.setScale(2, RoundingMode.HALF_DOWN)
        fun build(dripPrice: BigDecimal, bnbPrice: BigDecimal): DisplayManager = DisplayManager(dripPrice, bnbPrice)
    }
}
