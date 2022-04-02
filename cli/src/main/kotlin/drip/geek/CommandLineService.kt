package drip.geek

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options

class CommandLineService(args: Array<String>) {
    private val parser = DefaultParser()
    private val parsed: CommandLine = parser.parse(OPTIONS, args)

    fun hasReportFlag() = parsed.hasOption(OPTION_REPORT)
    fun hasWalletBNBBalancesFlag() = parsed.hasOption(OPTION_BNB_BALANCES)
    fun hasHydrationFlag() = parsed.hasOption(OPTION_HYDRATE)
    fun hasLowBalanceFindAndFundFlag() = parsed.hasOption(OPTION_FIND_AND_FUND)
    fun hasBnbSendFlag() = parsed.hasOption(OPTION_BNB_SEND)
    fun amountToSend() = requireNotNull(parsed.getOptionValue(OPTION_BNB_SEND)).toBigDecimal()
    fun generateSecretKey() = parsed.hasOption(GENERATE_KEY)
    fun hasPrivateKeyToTokens() = parsed.hasOption(PRIVATE_KEY_TO_TOKENS)

    companion object {
        private const val OPTION_REPORT = "summarize_wallets"
        private const val OPTION_HYDRATE = "hydrate_wallets"
        private const val OPTION_BNB_BALANCES = "bnb_balances_wallets"
        private const val OPTION_FIND_AND_FUND = "find_and_fund_wallets"
        private const val OPTION_BNB_SEND = "send_bnb_to_wallets"
        private const val BNB_AMOUNT_TO_SEND = "bnb_amount"
        private const val GENERATE_KEY = "generate_key"
        private const val PRIVATE_KEY_TO_TOKENS = "generate_tokens"
        val OPTIONS = Options().apply {
            addOption(OPTION_REPORT, false, "Print wallet reports and wallets summary")
            addOption(OPTION_HYDRATE, false, "Hydrate all wallets")
            addOption(OPTION_BNB_BALANCES, false, "Print bnb balances for all wallets")
            addOption(OPTION_FIND_AND_FUND, false, "Finds all fundable wallets with a balance at or below \$MIN_BNB_BALANCE and sends them the amount of BNB defined in \$FUND_BNB_BALANCE")
            addOption(GENERATE_KEY, false, "Generates a random secret key")
            addOption(PRIVATE_KEY_TO_TOKENS, false, "Takes a comma separated list of strings at \$CONVERT_TO_TOKENS and tokenizes them and then lists them in console")
            addOption(Option.builder()
                .longOpt(OPTION_BNB_SEND)
                .argName(BNB_AMOUNT_TO_SEND)
                .hasArg()
                .desc("Send BNB from a main wallet to 1 or more other wallets")
                .build())
        }
    }
}
