package drip.geek

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import java.math.BigDecimal
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.web3j.abi.datatypes.Address
import org.web3j.crypto.Credentials
import org.web3j.protocol.Web3j
import org.web3j.protocol.http.HttpService

class DripNetworkControlTest {
    private lateinit var dripNetworkControl: DripNetworkControl
    private lateinit var web3Client: Web3Client
    private lateinit var testWallets: List<DripWallet>
    private val web3j: Web3j = mockk()
    private val faucet: Faucet = mockk()
    private val dripWallet1: DripWallet = mockk()
    private val dripWallet2: DripWallet = mockk()
    private val dripWallet3: DripWallet = mockk()
    private val httpService: HttpService = mockk()
    private val displayManager: DisplayManager = mockk()
    private val transactionResponse: TransactionResponse = mockk()

    @BeforeEach
    fun setup() {
        mockkObject(RestClient)
        mockkObject(DripWallets)
        mockkObject(DisplayManager)
        mockkObject(Faucet.Companion)
        mockkObject(Web3Client.Companion)

        web3Client = Web3Client(
            httpService = httpService,
            web3j = web3j,
            isClosed = false
        )

        testWallets = listOf(dripWallet1, dripWallet2, dripWallet3)

        every { testWallets[0].address } returns WALLET_ADDRESSES[0]
        every { testWallets[1].address } returns WALLET_ADDRESSES[1]
        every { testWallets[2].address } returns WALLET_ADDRESSES[2]
        // Make the first wallet the main wallet
        every { DripWallets.MAIN_BNB_WALLET } returns testWallets[0]
        dripNetworkControl = DripNetworkControl(testWallets)

        every { web3j.shutdown() } returns Unit
        every { httpService.close() } returns Unit
        every { Web3Client.init() } returns web3Client
        every { DisplayManager.build(any(), any()) } returns displayManager
    }

    @AfterEach
    fun cleanup() {
        unmockkAll()
    }

    @Test
    fun `wallets report test`() {
        every { RestClient.currentDripPrice() } returns DRIP_PRICE
        every { RestClient.currentBNBPrice() } returns BNB_PRICE
        every { Faucet.build(any(), any()) } returns faucet
        every { displayManager.displayWalletReport(any(), any(), any(), any(), any()) } returns Unit
        every { displayManager.displayWalletReportSummary(any()) } returns Unit

        testWallets.forEachIndexed { num, wallet ->
            val walletName = "Wallet $num"
            every { wallet.name } returns walletName
            every { wallet.address } returns WALLET_ADDRESSES[num]
            every { wallet.fundable } returns true
            every { wallet.credentials } returns WALLET_CREDENTIALS
            every { faucet.walletStats(any(), walletName, MIN_BNB_BALANCE) } returns DRIP_WALLET_STATS.copy(name = walletName)
        }

        dripNetworkControl.walletsReport()

        verify { Web3Client.init() }
        verify { web3j.shutdown() }
        verify { httpService.close() }
    }

    @Test
    fun `wallets BNB report test`() {
        val processName = "drip-network-control"
        every { RestClient.currentDripPrice() } returns DRIP_PRICE
        every { RestClient.currentBNBPrice() } returns BNB_PRICE
        every { Faucet.build(any(), any()) } returns faucet
        every { displayManager.displayLine() } returns Unit
        every { displayManager.displayWalletBNBReport(any(), any(), any(), any(), any(), any()) } returns Unit
        every { displayManager.displayWalletBNBReportSummary(any(), any(), any()) } returns Unit

        // We want to test having a bnb balance of HEALTHY, LOW_BALANCE and NEEDS_REFILL
        val walletBalance1 = 0.toBigDecimal()
        val walletBalance2 = MIN_BNB_BALANCE + BigDecimal.ONE
        val walletBalance3 = FUND_BNB_BALANCE

        testWallets.forEachIndexed { num, wallet ->
            val walletFaucet: Faucet = mockk()
            val walletName = "Wallet $num"
            every { wallet.name } returns walletName
            every { wallet.address } returns WALLET_ADDRESSES[num]
            every { wallet.fundable } returns true
            every { wallet.credentials } returns WALLET_CREDENTIALS
            every { walletFaucet.walletStats(any(), walletName, MIN_BNB_BALANCE) } returns DRIP_WALLET_STATS.copy(name = walletName)
            every { walletFaucet.bnbBalance() } returns when (num) {
                0 -> walletBalance1
                1 -> walletBalance2
                else -> walletBalance3
            }
            every { walletFaucet.name } returns walletName
            every { Faucet.build(wallet, any()) } returns walletFaucet
        }

        val mainWalletName = DripWallets.MAIN_BNB_WALLET.name
        every { DripWallets.MAIN_BNB_WALLET.name } returns testWallets[0].name
        every { DripWallets.MAIN_BNB_WALLET.address } returns testWallets[0].address
        every { faucet.name } returns mainWalletName
        every { faucet.address } returns DripWallets.MAIN_BNB_WALLET.address
        every { faucet.bnbBalance() } returns MAIN_WALLET_BNB_BALANCE
        every { faucet.walletStats(any(), mainWalletName, MIN_BNB_BALANCE) } returns DRIP_WALLET_STATS.copy(name = mainWalletName)
        every { Faucet.build(DripWallets.MAIN_BNB_WALLET, any()) } returns faucet

        dripNetworkControl.walletsBNBReport()


        verify { displayManager.displayWalletBNBReport(processName, "Wallet 0", walletBalance1, BNBBalanceHealth.NEEDS_REFILL, 1, 3) }
        verify { displayManager.displayWalletBNBReport(processName, "Wallet 1", walletBalance2, BNBBalanceHealth.LOW_BALANCE, 2, 3) }
        verify { displayManager.displayWalletBNBReport(processName, "Wallet 2", walletBalance3, BNBBalanceHealth.HEALTHY, 3, 3) }
        verify { displayManager.displayWalletBNBReportSummary(processName, FUND_BNB_BALANCE, walletBalance1) }
        verify(exactly = 2) { Web3Client.init() }
        verify(exactly = 2) { web3j.shutdown() }
        verify(exactly = 2) { web3Client.close() }
        verify { httpService.close() }
    }

    @Test
    fun `send BNB to wallets test`() {
        val bnbSendAmount = BigDecimal("3")
        every { RestClient.currentDripPrice() } returns DRIP_PRICE
        every { RestClient.currentBNBPrice() } returns BNB_PRICE

        testWallets.forEachIndexed() { num, wallet ->
            val walletName = "Wallet $num"
            val walletFaucet: Faucet = mockk()
            every { wallet.name } returns walletName
            every { wallet.address } returns WALLET_ADDRESSES[num]
            every { wallet.fundable } returns true
            every { wallet.credentials } returns WALLET_CREDENTIALS
            every { walletFaucet.name } returns walletName
            every { walletFaucet.address } returns WALLET_ADDRESSES[num]
            every { walletFaucet.bnbBalance() } returns MAIN_WALLET_BNB_BALANCE
            every { walletFaucet.walletStats(any(), walletName, MIN_BNB_BALANCE) } returns DRIP_WALLET_STATS.copy(name = walletName)
            every { Faucet.build(wallet, any()) } returns walletFaucet
        }

        val mainWalletName = DripWallets.MAIN_BNB_WALLET.name
        every { DripWallets.MAIN_BNB_WALLET.name } returns testWallets[0].name
        every { DripWallets.MAIN_BNB_WALLET.address } returns testWallets[0].address
        every { faucet.name } returns mainWalletName
        every { faucet.address } returns DripWallets.MAIN_BNB_WALLET.address
        every { faucet.bnbBalance() } returns MAIN_WALLET_BNB_BALANCE
        every { faucet.walletStats(any(), mainWalletName, MIN_BNB_BALANCE) } returns DRIP_WALLET_STATS.copy(name = mainWalletName)
        every { Faucet.build(DripWallets.MAIN_BNB_WALLET, any()) } returns faucet
        every { faucet.sendBNBFunds(to = any(), amount = bnbSendAmount) } returns transactionResponse

        dripNetworkControl.sendBNBToWallets(bnbSendAmount)

        // testWallets[0] is the main wallet and may not send to itself.
        // It sends out BNB to the other two drip wallets
        verify { faucet.sendBNBFunds(to = WALLET_ADDRESSES[1], amount = bnbSendAmount) }
        verify { faucet.sendBNBFunds(to = WALLET_ADDRESSES[2], amount = bnbSendAmount) }
        verify(exactly = 2) { Web3Client.init() }
        verify(exactly = 2) { web3j.shutdown() }
        verify(exactly = 2) { web3Client.close() }
        verify { httpService.close() }
    }

    @Test
    fun `low BNB balance find and fund test`() {
        val bnbSendAmount = FUND_BNB_BALANCE
        every { RestClient.currentDripPrice() } returns DRIP_PRICE
        every { RestClient.currentBNBPrice() } returns BNB_PRICE

        testWallets.forEachIndexed { num, wallet ->
            val walletName = "Wallet $num"
            val walletFaucet: Faucet = mockk()
            every { wallet.name } returns walletName
            every { wallet.address } returns WALLET_ADDRESSES[num]
            every { wallet.fundable } returns true
            every { wallet.credentials } returns WALLET_CREDENTIALS
            every { walletFaucet.name } returns walletName
            every { walletFaucet.address } returns WALLET_ADDRESSES[num]
            every { walletFaucet.bnbBalance() } returns BALANCE_BELOW_MIN_BALANCE
            every { walletFaucet.walletStats(any(), walletName, MIN_BNB_BALANCE) } returns DRIP_WALLET_STATS.copy(name = walletName)
            every { Faucet.build(wallet, any()) } returns walletFaucet
        }

        val mainWalletName = DripWallets.MAIN_BNB_WALLET.name
        every { DripWallets.MAIN_BNB_WALLET.name } returns testWallets[0].name
        every { DripWallets.MAIN_BNB_WALLET.address } returns testWallets[0].address
        every { faucet.name } returns mainWalletName
        every { faucet.address } returns DripWallets.MAIN_BNB_WALLET.address
        every { faucet.bnbBalance() } returns MAIN_WALLET_BNB_BALANCE
        every { faucet.walletStats(any(), mainWalletName, MIN_BNB_BALANCE) } returns DRIP_WALLET_STATS.copy(name = mainWalletName)
        every { Faucet.build(DripWallets.MAIN_BNB_WALLET, any()) } returns faucet
        every { faucet.sendBNBFunds(to = any(), amount = bnbSendAmount) } returns transactionResponse

        dripNetworkControl.lowBNBBalanceFindAndFund()

        // testWallets[0] is the main wallet and may not send to itself.
        // It sends out BNB to the other two drip wallets
        verify { faucet.sendBNBFunds(to = WALLET_ADDRESSES[1], amount = bnbSendAmount) }
        verify { faucet.sendBNBFunds(to = WALLET_ADDRESSES[2], amount = bnbSendAmount) }
        verify(exactly = 2) { Web3Client.init() }
        verify(exactly = 2) { web3j.shutdown() }
        verify(exactly = 2) { web3Client.close() }
        verify { httpService.close() }
    }

    @Test
    fun `hydrate wallets`() {
        val processName = "drip-network-control"
        val txHash = "lets-pretend-this-is-a-sexy-transaction-hash"
        every { RestClient.currentDripPrice() } returns DRIP_PRICE
        every { RestClient.currentBNBPrice() } returns BNB_PRICE
        every { Faucet.build(any(), any()) } returns faucet
        every { displayManager.startHydration(any()) } returns Unit
        every { displayManager.displayWalletStats(any(), any(), any(), any(), any()) } returns Unit
        every { displayManager.displayHydrationInfo(any(), any(), any(), any()) } returns Unit
        every { displayManager.displaySummary(any()) } returns Unit

        testWallets.forEachIndexed() { num, wallet ->
            val walletName = "Wallet $num"
            val walletStats = DRIP_WALLET_STATS.copy(name = walletName)
            val walletFaucet: Faucet = mockk()
            every { wallet.name } returns walletName
            every { wallet.address } returns WALLET_ADDRESSES[num]
            every { wallet.fundable } returns true
            every { wallet.credentials } returns WALLET_CREDENTIALS
            every { walletFaucet.name } returns walletName
            every { walletFaucet.address } returns WALLET_ADDRESSES[num]
            every { walletFaucet.bnbBalance() } returns MAIN_WALLET_BNB_BALANCE
            every { walletFaucet.walletStats(any(), walletName, MIN_BNB_BALANCE) } returns walletStats
            every { walletFaucet.hydrate() } returns TransactionResponse.Success(transactionHash = txHash)
            every { walletFaucet.newDepositAmount() } returns walletStats.depositBalance + FUND_BNB_BALANCE
            every { Faucet.build(wallet, any()) } returns walletFaucet
        }

        dripNetworkControl.hydrateWallets()

        verify { displayManager.startHydration(processName) }
        testWallets.forEachIndexed { num, wallet ->
            verify {
                displayManager.displayHydrationInfo(
                    processName = processName,
                    walletStats = DRIP_WALLET_STATS.copy(name = "Wallet $num"),
                    transactionHash = txHash,
                    newDepositAmount = DRIP_WALLET_STATS.depositBalance + FUND_BNB_BALANCE
                )
            }
        }
        verify { displayManager.displaySummary(processName) }
    }

    companion object {
        private val DRIP_PRICE = BigDecimal.valueOf(10000)
        private val BNB_PRICE = BigDecimal.valueOf(420)
        private val MAIN_WALLET_BNB_BALANCE = BigDecimal.valueOf(10000)
        private val BALANCE_BELOW_MIN_BALANCE = BigDecimal.valueOf(1)
        private val FUND_BNB_BALANCE = BigDecimal.valueOf(10)
        private val MIN_BNB_BALANCE = BigDecimal.valueOf(5)
        private val WALLET_ADDRESS_1 = Address("0xc0ffee254749296a45a3885639AC7E10F9d54977")
        private val WALLET_ADDRESS_2 = Address("0xc0ffee254749296a45a3885639AC7E10F9d54978")
        private val WALLET_ADDRESS_3 = Address("0xc0ffee254749296a45a3885639AC7E10F9d54979")
        private val WALLET_ADDRESSES = listOf(WALLET_ADDRESS_1, WALLET_ADDRESS_2, WALLET_ADDRESS_3)
        private val WALLET_CREDENTIALS = Credentials.create(
            "8f2a55949038a9610f50fb23b5883af3b4ecb3c3bb792cbcefbd1542c692be63"
        )
        private val DRIP_WALLET_STATS = DripWalletStats(
            "fred",
            BigDecimal.TEN,
            BigDecimal.TEN,
            BigDecimal.TEN,
            BigDecimal.TEN,
            BigDecimal.TEN,
            BNBBalanceHealth.HEALTHY
        )
    }
}
