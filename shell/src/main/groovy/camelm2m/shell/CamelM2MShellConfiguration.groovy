package camelm2m.shell

import camelm2m.shell.endpoints.DeployEndpoint
import camelm2m.shell.endpoints.DeployMvcEndpoint
import camelm2m.shell.endpoints.DetectDevicesEndpoint
import camelm2m.shell.endpoints.DetectRpiDevicesEndpoint
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import zed.deployer.manager.DeployablesManager

@SpringBootApplication
@ComponentScan(['zed', 'org.springframework.boot.autoconfigure.spotifydocker'])
class CamelM2MShellConfiguration {

    static void main(String... args) {
        new SpringApplication(CamelM2MShellConfiguration.class).run(args)
    }

    @Bean
    DetectDevicesEndpoint detectDevicesEndpoint() {
        new DetectDevicesEndpoint()
    }

    @Bean
    DetectRpiDevicesEndpoint detectRpiDevicesEndpoint() {
        new DetectRpiDevicesEndpoint()
    }

    @Bean
    DeployMvcEndpoint deployMvcEndpoint(DeployablesManager deployablesManager) {
        new DeployMvcEndpoint(new DeployEndpoint(), deployablesManager)
    }
}
