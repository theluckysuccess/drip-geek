package drip.geek

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
