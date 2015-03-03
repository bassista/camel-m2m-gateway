package camelm2m.benchmarks.mqtt.forward

import org.apache.activemq.broker.BrokerService
import org.apache.camel.EndpointInject
import org.apache.camel.RoutesBuilder
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = [MqttForwardBenchmarkTestConfiguration.class, MqttForwardBenchmark.class])
class MqttForwardBenchmarkTest extends Assert {

    @EndpointInject(uri = 'mock:test')
    MockEndpoint mock

    @Test
    void shouldSendMessageToMqttBroker() {
        mock.expectedMinimumMessageCount(1)
        mock.assertIsSatisfied()
    }

}

@Configuration
class MqttForwardBenchmarkTestConfiguration {

    @Bean
    RoutesBuilder mqttConsumer() {
        new RouteBuilder() {
            void configure() {
                from('paho:topic').to('mock:test')
            }
        }
    }

    @Bean(initMethod = 'start', destroyMethod = 'stop')
    BrokerService brokerService() {
        def broker = new BrokerService()
        broker.setBrokerName(getClass().getSimpleName())
        broker.setPersistent(false)
        broker.addConnector('mqtt://localhost:1883')
        broker
    }

}