package drip.geek

sealed class TransactionResponse {
    data class Error(val message: String) : TransactionResponse()
    data class Success(val transactionHash: String) : TransactionResponse()
}
