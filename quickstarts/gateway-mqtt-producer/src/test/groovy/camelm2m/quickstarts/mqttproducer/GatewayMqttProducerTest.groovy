package camelm2m.quickstarts.mqttproducer

import org.apache.activemq.broker.BrokerService
import org.apache.camel.EndpointInject
import org.apache.camel.RoutesBuilder
import org.apache.camel.builder.RouteBuilder
import org.apache.camel.component.mock.MockEndpoint
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import static camelm2m.quickstarts.mqttproducer.MqttProducerGatewayTestConfiguration.mqttPort
import static org.springframework.util.SocketUtils.findAvailableTcpPort

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = [MqttProducerGatewayTestConfiguration.class, GatewayMqttProducer.class])
class GatewayMqttProducerTest extends Assert {

    @EndpointInject(uri = 'mock:test')
    MockEndpoint mock

    @BeforeClass
    static void beforeClass() {
        System.setProperty('broker.url', "tcp://localhost:${mqttPort}")
    }

    @Test
    void shouldSendMessageToMqttBroker() {
        mock.expectedMinimumMessageCount(1)
        mock.assertIsSatisfied()
    }

}

@Configuration
class MqttProducerGatewayTestConfiguration {

    static int mqttPort = findAvailableTcpPort()

    @Bean
    RoutesBuilder mqttConsumer() {
        new RouteBuilder() {
            void configure() {
                from('paho:topic?brokerUrl={{broker.url}}').to('mock:test')
            }
        }
    }

    @Bean(initMethod = 'start', destroyMethod = 'stop')
    BrokerService brokerService() {
        def broker = new BrokerService()
        broker.setBrokerName(getClass().getSimpleName())
        broker.setPersistent(false)
        broker.addConnector("mqtt://localhost:${mqttPort}")
        broker
    }

}