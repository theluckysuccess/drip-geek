package drip.geek

import org.web3j.abi.datatypes.Address
import org.web3j.crypto.Credentials

data class DripWallet(
    val name: String,
    val address: Address,
    val credentials: Credentials,
    val fundable: Boolean
) {
    companion object {
        fun build(
            name: String,
            contractAddress: String,
            pk: String,
            fundable: Boolean
        ) = DripWallet(
            name = name,
            address = Address(contractAddress),
            credentials = Credentials.create(pk),
            fundable = fundable
        )
    }
}
