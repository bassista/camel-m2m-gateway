package org.apache.camel.component.paho;

import org.apache.camel.AsyncCallback;
import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.ExchangeBuilder;
import org.apache.camel.impl.DefaultConsumer;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PahoConsumer extends DefaultConsumer {

    public PahoConsumer(Endpoint endpoint, Processor processor) {
        super(endpoint, processor);
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        String topic = getEndpoint().getTopic();
        getEndpoint().getClient().subscribe(topic);
        getEndpoint().getClient().setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Exchange exchange = ExchangeBuilder.anExchange(getEndpoint().getCamelContext()).withBody(message.getPayload()).build();
                getAsyncProcessor().process(exchange, new AsyncCallback() {
                    @Override
                    public void done(boolean doneSync) {

                    }
                });
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    @Override
    protected void doStop() throws Exception {
        super.doStop();
        if (getEndpoint().getClient().isConnected()) {
            String topic = getEndpoint().getTopic();
            getEndpoint().getClient().unsubscribe(topic);
        }
    }

    @Override
    public PahoEndpoint getEndpoint() {
        return (PahoEndpoint) super.getEndpoint();
    }

}