package drip.geek

import kotlinx.serialization.Serializable

@Serializable
data class DripPrice(
    val time: Long,
    val value: Double
)
