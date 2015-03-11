package commands

import camelm2m.shell.utils.RpiDetector
import org.crsh.cli.Command
import org.crsh.command.InvocationContext

class detect_devices {

    private static final int INDEX_OF_LEADING_SLASH = 1

    @Command
    def main(InvocationContext context) {
        out.println('Detecting devices in your network...')
        out.flush()
        new RpiDetector().detectReachableAddresses().collect {
            it.toString().substring(INDEX_OF_LEADING_SLASH)
        }.join('\n')
    }

}