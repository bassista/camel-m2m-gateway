package camelm2m.shell

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
    DeployablesManager deployablesManager(ZedHome zedHome, DockerClient docker) {
        def deployDirectory = zedHome.deployDirectory()
        def workspaceFile = new File(deployDirectory, 'workspace')
        new FileSystemDeployablesManager(zedHome, workspaceFile, allDeployableHandlers(workspaceFile, docker))
    }

}
