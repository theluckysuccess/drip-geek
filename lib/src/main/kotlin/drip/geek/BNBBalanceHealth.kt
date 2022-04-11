package drip.geek

import java.math.BigDecimal

enum class BNBBalanceHealth(health: String) {
    HEALTHY("HEALTHY"), LOW_BALANCE("LOW BALANCE"), NEEDS_REFILL("NEEDS BNB REFILL");

    companion object {
        private val LOW_BNB_BALANCE_RATIO = BigDecimal(".60")
        fun bnbBalanceHealth(minBnbBalance: BigDecimal, walletBnbBalance: BigDecimal) = when {
            minBnbBalance == BigDecimal.ZERO -> NEEDS_REFILL
            walletBnbBalance <= minBnbBalance -> NEEDS_REFILL
            minBnbBalance / walletBnbBalance >= LOW_BNB_BALANCE_RATIO -> LOW_BALANCE
            else -> HEALTHY
        }
    }
}