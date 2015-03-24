package commands

import camelm2m.shell.endpoints.DetectRpiDevicesEndpoint
import org.crsh.cli.Command
import org.crsh.command.InvocationContext

class detect_raspberry_pi {

    @Command
    def main(InvocationContext context) {
        out.println('Detecting Raspberry Pi devices in your network...')
        out.flush()
        DetectRpiDevicesEndpoint detectRpiDevicesEndpoint = context.getAttributes().get('spring.beanfactory')
                .getBean(DetectRpiDevicesEndpoint.class)
        detectRpiDevicesEndpoint.invoke().join("\n")
    }

}