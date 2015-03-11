package org.apache.camel.component.paho;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PahoProducer extends DefaultProducer {

    public PahoProducer(PahoEndpoint endpoint) {
        super(endpoint);
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        MqttClient client = getEndpoint().getClient();
        String topic = getEndpoint().getTopic();
        int qos = getEndpoint().getQos();
        byte[] payload = exchange.getIn().getBody(byte[].class);

        MqttMessage message = new MqttMessage(payload);
        message.setQos(qos);
        client.publish(topic, message);
    }

    @Override
    public PahoEndpoint getEndpoint() {
        return (PahoEndpoint) super.getEndpoint();
    }

}