package drip.geek

import java.time.Clock

fun main(args: Array<String>) {
    DripNetworkControlCLI().run(args, Clock.systemDefaultZone())
}
