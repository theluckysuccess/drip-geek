package drip.geek

import io.github.cdimascio.dotenv.dotenv

class DripWallets(
    dripEnvPrefix: String = DRIP_ENV_PREFIX,
    secretKeyEnv: String = SECRET_KEY,
) {
    val wallets: List<DripWallet> = mutableListOf<DripWallet>().apply {
        for (e in dotenv().entries().filter { it.key.startsWith(dripEnvPrefix) }) {
            val (include, fundable, name, address, encodedPrivateKey) = e.value.split(",")
            if (include.toBooleanStrict()) {
                add(
                    DripWallet.build(
                        name = name,
                        contractAddress = address,
                        pk = SecretManager(dotenv()[secretKeyEnv]).decrypt(encodedPrivateKey),
                        fundable = fundable.toBooleanStrict()
                    )
                )
            }
        }
    }

    companion object {
        private const val DRIP_ENV_PREFIX: String = "__DRIP_"
        const val SECRET_KEY: String = "SECRET_KEY"
        val MAIN_BNB_WALLET: DripWallet
            get() = dotenv()["MAIN_BNB_WALLET"].split(",")
                .let { (_, _, name, address, encodedPrivateKey) ->
                    DripWallet.build(
                        name = name,
                        contractAddress = address,
                        pk = SecretManager(dotenv()[SECRET_KEY]).decrypt(encodedPrivateKey),
                        fundable = false
                    )
                }

        val CONVERT_TO_TOKENS
            get() = try {
                dotenv()["CONVERT_TO_TOKENS"]
            } catch (e: Exception) {
                null
            }
    }
}
