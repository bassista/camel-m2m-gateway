package camelm2m.shell

import camelm2m.shell.dto.PostBody
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.TestRestTemplate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.web.client.RestTemplate
import zed.deployer.handlers.FatJarMavenDeployableHandler
import zed.deployer.manager.DeployablesManager
import zed.deployer.manager.FileSystemDeployablesManager
import zed.deployer.manager.ZedHome

import static com.google.common.io.Files.createTempDir;
import static org.springframework.util.SocketUtils.findAvailableTcpPort

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = [CamelM2MShellConfiguration.class, CamelM2mShellTestTestConfiguration.class])
@WebAppConfiguration
@IntegrationTest
class CamelM2mShellTest extends Assert {

    private static int apiPort = findAvailableTcpPort()

    private static RestTemplate restTemplate;

    @Autowired
    DeployablesManager deploymentManager;

    @BeforeClass
    static void beforeClass() {
        System.setProperty("server.port", "${apiPort}")

        restTemplate = new TestRestTemplate()

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>()
        messageConverters.add(new MappingJackson2HttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
    }

    @Test
    void shouldReturn200ForOk() {
        // Given
        PostBody data = new PostBody(uri: "fatjar:mvn:com.google.guava/guava/18.0")

        // When
        def response = restTemplate.postForEntity("http://localhost:${apiPort}/deploy", data, ResponseEntity.class)

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.OK)
        assertTrue(Arrays.asList(deploymentManager.workspace().list()).contains("guava-18.0.jar"));
    }

    @Test
    void shouldReturn500ForError() {
        // Given
        PostBody data = new PostBody(uri: "mvn:com.google.guava/guava/18.0")

        // When
        def response = restTemplate.postForEntity("http://localhost:${apiPort}/deploy", data, ResponseEntity.class)

        // Then
        assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}

@Configuration
class CamelM2mShellTestTestConfiguration {

    @Autowired
    ZedHome zedHome;

    @Bean
    DeployablesManager deploymentManager() {
        File workspace = createTempDir();
        return new FileSystemDeployablesManager(zedHome, workspace, Arrays.asList(new FatJarMavenDeployableHandler(workspace)));
    }

}
