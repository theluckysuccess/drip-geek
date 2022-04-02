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
        logger.info { "process=$processName | Total Drip in faucet for ${walletStats.name} is now $$newDepositAmount" }
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
        walletBnbBalance: BigDecimal
    ) {
        logger.info { "process=$processName | Wallet Name: $walletName" }
        logger.info { "process=$processName | BNB balance: $walletBnbBalance" }
        totalBNB += walletBnbBalance
        totalBNBValue += walletBnbBalance.multiply(bnbPrice)
    }

    fun displayWalletBNBReportSummary(processName: String) {
        displayLine()
        logger.info { "process=$processName | Total BNB Amount: $totalBNB" }
        logger.info { "process=$processName | Total BNB Value:  $${totalBNBValue.toScale()}" }
        displayLine()
    }

    companion object : KLogging() {
        fun BigDecimal.toScale(): BigDecimal = this.setScale(2, RoundingMode.HALF_DOWN)
        fun build(dripPrice: BigDecimal, bnbPrice: BigDecimal): DisplayManager = DisplayManager(dripPrice, bnbPrice)
    }
}
