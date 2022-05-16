package drip.geek

import drip.geek.DisplayManager.Companion.toScale
import java.math.BigDecimal

data class DripWalletStats(
    val name: String,
    val bnbBalance: BigDecimal,
    val depositBalance: BigDecimal,
    val depositBalanceValue: BigDecimal,
    val availableBalance: BigDecimal,
    val claimedBalance: BigDecimal,
    val bnbBalanceHealth: BNBBalanceHealth,
)

fun DripWalletStats.claimableAsPercent(): BigDecimal =
    ((this.availableBalance / this.depositBalance) * BigDecimal.valueOf(100)).toScale()
