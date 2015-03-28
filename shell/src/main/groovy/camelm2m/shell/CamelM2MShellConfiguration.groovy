package camelm2m.shell

import camelm2m.shell.endpoints.DeployEndpoint
import camelm2m.shell.endpoints.DeployMvcEndpoint
import camelm2m.shell.endpoints.DetectDevicesEndpoint
import camelm2m.shell.endpoints.DetectRpiDevicesEndpoint
import com.github.dockerjava.api.DockerClient
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import zed.deployer.manager.DeployablesManager
import zed.deployer.manager.FileSystemDeployablesManager
import zed.deployer.manager.ZedHome

import static zed.deployer.handlers.DeployableHandlers.allDeployableHandlers

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
    DeployEndpoint deployEndpoint() {
        new DeployEndpoint()
    }

    @Bean
    DeployMvcEndpoint deployMvcEndpoint(DeployEndpoint deployEndpoint, DeployablesManager deployablesManager) {
        new DeployMvcEndpoint(deployEndpoint, deployablesManager)
    }
}
