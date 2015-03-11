package org.apache.camel.component.paho;

import org.apache.activemq.broker.BrokerService;
import org.apache.camel.EndpointInject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.AvailablePortFinder;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class PahoComponentTest extends CamelTestSupport {

    MqttConnectOptions connectOptions = Mockito.mock(MqttConnectOptions.class);

    @EndpointInject(uri = "mock:test")
    MockEndpoint mock;

    BrokerService broker;

    int mqttPort = AvailablePortFinder.getNextAvailable();

    @Override
    protected boolean useJmx() {
        return false;
    }

    @Override
    public void doPreSetup() throws Exception {
        super.doPreSetup();
        broker = new BrokerService();
        broker.setPersistent(false);
        broker.addConnector("mqtt://localhost:" + mqttPort);
        broker.start();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        broker.stop();
    }

    @Test
    public void shouldReadMessageFromMqtt() throws InterruptedException {
        // Given
        String msg = "msg";
        mock.expectedBodiesReceived(msg);

        // When
        template.sendBody("mock:test", msg);

        // Then
        mock.assertIsSatisfied();
    }

    @Test
    public void shouldNotReadMessageFromUnregisteredTopic() throws InterruptedException {
        // Given
        mock.expectedMessageCount(0);

        // When
        template.sendBody("paho:someRandomQueue?brokerUrl=tcp://localhost:" + mqttPort, "msg");

        // Then
        mock.assertIsSatisfied();
    }

    @Test
    public void shouldUseConnectionOptionsFromRegistry() {
        verify(connectOptions).getServerURIs();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("direct:test").to("paho:queue?brokerUrl=tcp://localhost:" + mqttPort);

                from("paho:queue?brokerUrl=tcp://localhost:" + mqttPort).to("mock:test");

                from("paho:persistenceTest?persistence=FILE&brokerUrl=tcp://localhost:" + mqttPort).to("mock:persistenceTest");

                from("direct:connectOptions").to("paho:queue?connectOptions=#connectOptions&brokerUrl=tcp://localhost:" + mqttPort);
            }
        };
    }

    @Override
    protected JndiRegistry createRegistry() throws Exception {
        JndiRegistry registry = super.createRegistry();
        registry.bind("connectOptions", connectOptions);
        return registry;
    }

}
