package commands

import camelm2m.shell.endpoints.DetectDevicesEndpoint
import org.crsh.cli.Command
import org.crsh.command.InvocationContext

class detect_devices {

    @Command
    def main(InvocationContext context) {
        out.println('Detecting devices in your network...')
        out.flush()
        DetectDevicesEndpoint detectDevicesEndpoint = context.getAttributes().get('spring.beanfactory')
                .getBean(DetectDevicesEndpoint.class)
        detectDevicesEndpoint.invoke().join("\n")
    }

}