package drip.geek

import java.math.BigDecimal

sealed class HydrationStyle(val canHydrate: (BigDecimal, BigDecimal) -> Boolean) {
    object ALL : HydrationStyle(canHydrate = { availableBalance, _ -> availableBalance >= BigDecimal.ZERO })
    object ByDepositSize : HydrationStyle(canHydrate = { availableBalance, minAvailableBalanceToHydrate ->
        availableBalance >= minAvailableBalanceToHydrate
    })
}
