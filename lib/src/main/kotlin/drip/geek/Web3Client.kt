package drip.geek

import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

class Web3Client(
    private var isClosed: Boolean = true,
    private var httpService: HttpService,
    private var web3j: Web3j,
) {
    fun web3j(): Web3j {
        if (isClosed) throw RuntimeException("Web3Client is closed and should be initialized before use")
        return web3j
    }

    fun close() {
        web3j.shutdown()
        httpService.close()
        isClosed = true
    }

    companion object {
        private const val BINANCE_SMART_CHAIN = "https://bsc-dataseed.binance.org/"
        fun init() = HttpService(BINANCE_SMART_CHAIN).let { httpService ->
            Web3Client(
                httpService = httpService,
                web3j = Web3j.build(httpService),
                isClosed = false
            )
        }
    }
}
