package drip.geek

import com.macasaet.fernet.Key
import io.github.cdimascio.dotenv.dotenv
import mu.KLogging
import org.apache.commons.cli.HelpFormatter

class DripNetworkControlCLI() {
    fun run(args: Array<String>) {
        try {
            val commandLine = CommandLineService(args)
            when {
                args.isEmpty() -> {
                    logger.error { "Arguments required" }
                    HelpFormatter().printHelp(
                        "java -jar cli.jar",
                        CommandLineService.OPTIONS,
                        true
                    )
                }

                commandLine.hasLowBalanceFindAndFundFlag() && commandLine.hasHydrationFlag() ->
                    DripWallets().wallets.let {
                        DripNetworkControl(it).lowBNBBalanceFindAndFund()
                        DripNetworkControl(it).hydrateWallets()
                    }

                commandLine.hasHydrationFlag() ->
                    DripNetworkControl(DripWallets().wallets).hydrateWallets()

                commandLine.hasLowBalanceFindAndFundFlag() ->
                    DripNetworkControl(DripWallets().wallets).lowBNBBalanceFindAndFund()

                commandLine.hasWalletBNBBalancesFlag() ->
                    DripNetworkControl(DripWallets().wallets).walletsBNBReport()

                commandLine.hasBnbSendFlag() ->
                    DripNetworkControl(DripWallets().wallets).sendBNBToWallets(commandLine.amountToSend())

                commandLine.hasReportFlag() ->
                    DripNetworkControl(DripWallets().wallets).walletsReport()

                // Strongly suggest that you delete CONVERT_TO_TOKENS environment variable when you are done
                commandLine.hasPrivateKeyToTokens() -> {
                    val secretManager = SecretManager(dotenv()[DripWallets.SECRET_KEY])
                    var count = 1
                    val tokens = DripWallets.CONVERT_TO_TOKENS
                    when {
                        tokens.isNullOrBlank() -> logger.info { "CONVERT_TO_TOKENS not set, empty or invalid" }
                        else -> tokens.split(",").map {
                            val token = secretManager.encrypt(it)
                            logger.info { "Tokenized String $count: ${token.serialise()}" }
                            count++
                        }
                    }
                }

                commandLine.generateSecretKey() -> logger.info {
                    "Generated secret key: ${
                        Key.generateKey().serialise()
                    }"
                }

                else -> HelpFormatter().printHelp(
                    "java -jar drip-geek.jar",
                    CommandLineService.OPTIONS,
                    true
                )
            }
        } catch (e: Exception) {
            logger.error("process=$PROCESS_NAME: ${e.localizedMessage}")
        }
    }

    companion object : KLogging() {
        private const val PROCESS_NAME = "drip-network-control-cli"
    }
}
