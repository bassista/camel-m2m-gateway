package camelm2m.benchmarks.mqtt.forward

import org.apache.activemq.broker.BrokerService
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MqttForwardBenchmarkTest.class)
class MqttForwardBenchmarkTest extends Assert {

    @Test
    void shouldPerformSmokeTest() {
    }

    @Bean(initMethod = 'start', destroyMethod = 'close')
    BrokerService brokerService() {
        def broker = new BrokerService()
        broker.addConnector('mqtt+nio://localhost:1883')
        broker
    }

}
