package drip.geek

import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import mu.KLogging
import org.web3j.abi.datatypes.Address
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.protocol.Web3j
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.tx.RawTransactionManager
import org.web3j.tx.TransactionManager
import org.web3j.tx.TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH
import org.web3j.tx.TransactionManager.DEFAULT_POLLING_FREQUENCY
import org.web3j.tx.Transfer
import org.web3j.tx.gas.DefaultGasProvider
import org.web3j.tx.response.PollingTransactionReceiptProcessor
import org.web3j.tx.response.TransactionReceiptProcessor
import org.web3j.utils.Convert

internal class Faucet(
    private val dripWallet: DripWallet,
    web3Client: Web3Client,
) {
    private val web3j: Web3j = web3Client.web3j()
    private val contract: FaucetV4 = FaucetV4.load(
        FAUCET_ADDRESS,
        web3j,
        dripWallet.credentials,
        DefaultGasProvider()
    )

    val name: String
        get() = dripWallet.name
    val address: Address
        get() = dripWallet.address

    private fun availableBalance(): BigDecimal = contract.claimsAvailable(dripWallet.address).send().toValue()

    private fun dripInfo(): Pair<BigDecimal, BigDecimal> {
        val (_, depositAmount, claimAmount, _, _, _) = contract.userInfoTotals(dripWallet.address).sendAsync().get()
        return depositAmount.toValue() to claimAmount.toValue()
    }

    fun walletStats(dripPrice: BigDecimal, name: String, minBnbBalance: BigDecimal): DripWalletStats {
        val walletBNBValue = bnbBalance()
        val availableBalance = availableBalance()
        val (depositAmount, claimedAmount) = dripInfo()
        val depositValue = depositAmount * dripPrice
        return DripWalletStats(
            name = name,
            bnbBalance = walletBNBValue,
            availableBalance = availableBalance,
            depositBalance = depositAmount,
            claimedBalance = claimedAmount,
            depositBalanceValue = depositValue,
            bnbBalanceHealth = BNBBalanceHealth.bnbBalanceHealth(minBnbBalance, walletBNBValue)
        )
    }

    fun newDepositAmount(): BigDecimal = dripInfo().first

    fun sendBNBFunds(
        to: Address,
        amount: BigDecimal
    ): TransactionResponse = try {
        val txManager: TransactionManager = RawTransactionManager(web3j, dripWallet.credentials)
        val transfer = Transfer(web3j, txManager)
        val future = transfer.sendFunds(
            to.value,
            amount,
            Convert.Unit.ETHER,
            HYDRATE_GAS_PRICE,
            HYDRATE_GAS_LIMIT,
        ).sendAsync()

        while (!future.isDone) {
            print(".")
            Thread.sleep(500)
        }
        TransactionResponse.Success(future.get().transactionHash)
    } catch (e: Exception) {
        TransactionResponse.Error(e.message!!)
    }

    fun hydrate(): TransactionResponse {
        val txManager: TransactionManager = RawTransactionManager(web3j, dripWallet.credentials)

        val transactionResponse = txManager.sendTransaction(
            HYDRATE_GAS_PRICE,
            HYDRATE_GAS_LIMIT,
            FAUCET_ADDRESS,
            contract.roll().encodeFunctionCall(),
            BigInteger.ZERO
        )

        val receiptProcessor: TransactionReceiptProcessor = PollingTransactionReceiptProcessor(
            web3j,
            DEFAULT_POLLING_FREQUENCY,
            DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH
        )

        return when (transactionResponse.hasError()) {
            true -> TransactionResponse.Error(transactionResponse.error.message)
            false -> receiptProcessor.waitForTransactionReceipt(transactionResponse.transactionHash).let {
                TransactionResponse.Success(it.transactionHash)
            }
        }
    }

    fun bnbBalance(): BigDecimal {
        val balance = web3j.ethGetBalance(
            dripWallet.address.value,
            DefaultBlockParameter.valueOf("latest")
        ).send().balance

        return balance.toBigDecimal().divide(DIVIDE_FOR_CORRECT_DECIMAL_PLACE).setScale(4, RoundingMode.UP)
    }

    companion object : KLogging() {
        private const val FAUCET_ADDRESS = "0xFFE811714ab35360b67eE195acE7C10D93f89D8C"
        private val HYDRATE_GAS_LIMIT = BigInteger("500000")
        private val HYDRATE_GAS_PRICE = Convert.toWei("5", Convert.Unit.GWEI).toBigInteger()
        private val DIVIDE_FOR_CORRECT_DECIMAL_PLACE = BigInteger("1000000000000000000").toBigDecimal()
        private fun Uint256.toValue() = this.value.toBigDecimal().div(BigDecimal("1000000000000000")).movePointLeft(3)
        fun build(dripWallet: DripWallet, web3Client: Web3Client): Faucet = Faucet(dripWallet, web3Client)
    }
}
