package drip.geek

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Clock
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit
import mu.KLogging


class DisplayManager(
    private val dripPrice: BigDecimal,
    private val bnbPrice: BigDecimal,
    private val clock: Clock,
) {
    var totalDrip: BigDecimal = BigDecimal.ZERO
    var totalDripValue: BigDecimal = BigDecimal.ZERO
    var totalBNB: BigDecimal = BigDecimal.ZERO
    var totalBNBCost: BigDecimal = BigDecimal.ZERO
    var totalBNBValue: BigDecimal = BigDecimal.ZERO
    var totalHealthyBNBBalanceWallets: BigDecimal = BigDecimal.ZERO
    var totalLowBNBBalanceWallets: BigDecimal = BigDecimal.ZERO
    var totalNeedRefillBNBBalanceWallets: BigDecimal = BigDecimal.ZERO
    private var totalDripCompounded: BigDecimal = BigDecimal.ZERO
    private var totalDripCompoundedValue: BigDecimal = BigDecimal.ZERO
    private var totalBNBCompoundedCostValue: BigDecimal = BigDecimal.ZERO

    fun startHydration(processName: String) {
        logger.info { "============================" }
        logger.info { "process=$processName | Starting process: hydrating regular wallets" }
        displayTime(processName)
        logger.info { "process=$processName | Current price of drip is: $$dripPrice" }
    }

    fun displayTime(processName: String) {
        logger.info { "process=$processName | Time: ${clock.toFormattedTimeString()}" }
    }

    fun displayWalletStats(
        processName: String,
        dripWalletStats: DripWalletStats,
        balanceNeededToHydrate: BigDecimal,
        count: Int,
        total: Int,
    ) {
        logger.info { "============================" }
        logger.info { "process=$processName | Wallet Name:                ${dripWalletStats.name}" }
        logger.info { "process=$processName | Count:                      $count of $total" }
        logger.info { "process=$processName | BNB Balance:                ${dripWalletStats.bnbBalance}" }
        logger.info { "process=$processName | BNB Balance health:         ${dripWalletStats.bnbBalanceHealth}" }
        logger.info { "process=$processName | Deposit Amount:             ${dripWalletStats.depositBalance}" }
        logger.info { "process=$processName | Deposit Value:              $${dripWalletStats.depositBalanceValue.toScale()}" }
        logger.info { "process=$processName | Claimable Amount:           ${dripWalletStats.availableBalance}" }
        logger.info { "process=$processName | Claimable Amount Deposit %: ${dripWalletStats.claimableAsPercent()}%" }
        logger.info { "process=$processName | Claimed Amount:             ${dripWalletStats.claimedBalance}" }
        logger.info { "process=$processName | Balance Need to Hydrate:    $balanceNeededToHydrate" }
        logger.info { "============================" }
    }

    fun displayHydrationInfo(
        processName: String,
        oldAvailableBalance: BigDecimal,
        walletName: String,
        transactionHash: String,
        newDepositAmount: BigDecimal,
        preHydrationWalletBNBAmount: BigDecimal,
        postHydrationWalletBNBAmount: BigDecimal,
    ) {
        val newDepositValue = newDepositAmount.multiply(dripPrice)
        logger.info { "process=$processName | Hydrated $walletName!" }
        logger.info { "process=$processName | Transaction Hash: https://bscscan.com/tx/$transactionHash" }
        logger.info { "process=$processName | Added $oldAvailableBalance to deposit for $walletName" }
        logger.info { "process=$processName | Total Drip in faucet for $walletName is now $newDepositAmount and is worth $${newDepositValue.toScale()}" }
        totalDrip += newDepositAmount
        totalDripValue += newDepositValue
        totalDripCompounded += oldAvailableBalance
        totalDripCompoundedValue += oldAvailableBalance.multiply(dripPrice)
        (preHydrationWalletBNBAmount - postHydrationWalletBNBAmount).let { costToHydrateInBNB ->
            totalBNBCost += costToHydrateInBNB
            totalBNBCompoundedCostValue += costToHydrateInBNB.multiply(bnbPrice)
        }
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
        logger.info { "process=$processName | Total Deposit Amount: \$${totalDripValue.toScale()} ($totalDrip \$DRIP)" }
        logger.info { "process=$processName | Total Compounded Amount: \$${totalDripCompoundedValue.toScale()} ($totalDripCompounded \$DRIP)" }
        logger.info { "process=$processName | Total Compounded Cost: \$${totalBNBCompoundedCostValue.toScale()} ($totalBNBCost \$BNB)" }
        displayLine()
    }

    fun displayWalletReport(
        processName: String,
        walletStats: DripWalletStats,
        minAvailableBalanceToHydrate: BigDecimal,
        count: Int,
        total: Int,
    ) {
        displayWalletStats(processName, walletStats, minAvailableBalanceToHydrate, count, total)
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
        count: Int,
        total: Int,
    ) {
        logger.info { "process=$processName | Wallet Name:        $walletName" }
        logger.info { "process=$processName | Count:              $count of $total" }
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
        logger.info {
            "process=$processName | Current BNB fund amount value:            $${
                fundBNBBalance.multiply(
                    bnbPrice
                ).toScale()
            }"
        }
        logger.info { "process=$processName | BNB balance of main wallet:               $bnbBalance" }
        logger.info {
            "process=$processName | BNB balance value of main wallet:         $${
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
        private val formatter: DateTimeFormatter = DateTimeFormatter
            .ofPattern("E MMM dd yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault())

        fun Clock.toFormattedTimeString(): String = formatter.format(this.instant())
        fun BigDecimal.toScale(): BigDecimal = this.setScale(2, RoundingMode.HALF_DOWN)
        fun build(dripPrice: BigDecimal, bnbPrice: BigDecimal, clock: Clock): DisplayManager =
            DisplayManager(dripPrice, bnbPrice, clock)
    }
}
