package drip.geek

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object RestClient {

    private const val dripPricesUrl = "https://api.drip.community/prices/"
    private const val bnbPricesUrl = "https://api.pancakeswap.info/api/v2/tokens/0xbb4CdB9CBd36B01bD1cBaEBF2De08d9173bc095c"

    private fun httpClient() = HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    private suspend fun getDripPrices(): List<DripPrice> = httpClient().let { client ->
        client.get<List<DripPrice>>(dripPricesUrl).also { client.close() }
    }

    private suspend fun getBnbPrice(): BnbPrice = httpClient().let { client ->
        client.get<BnbPrice>(bnbPricesUrl).also { client.close() }
    }

    fun currentDripPrice() = runBlocking {
        getDripPrices().last().value.toBigDecimal()
    }

    fun currentBNBPrice() = runBlocking {
        getBnbPrice().data.price.toBigDecimal()
    }
}
