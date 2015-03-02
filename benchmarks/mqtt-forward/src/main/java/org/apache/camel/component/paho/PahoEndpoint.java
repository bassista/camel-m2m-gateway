package org.apache.camel.component.paho;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class PahoEndpoint extends DefaultEndpoint {

    String broker = "tcp://iot.eclipse.org:1883";
    String clientId = "JavaSample";
    MemoryPersistence persistence = new MemoryPersistence();

    // Configuration members

    private String topic;

    private int qos = 2;

    // Auto-configuration members

    private MqttClient client;

    public PahoEndpoint(String uri, Component component) {
        super(uri, component);
        if (topic == null) {
            topic = uri.substring(7);
        }
    }

    @Override
    protected void doStart() throws Exception {
        super.doStart();
        client = new MqttClient(broker, clientId, persistence);
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect();
    }

    @Override
    protected void doStop() throws Exception {
        getClient().disconnect();
        super.doStop();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new PahoProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public PahoComponent getComponent() {
        return (PahoComponent) super.getComponent();
    }

    // Configuration getters & setters

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public int getQos() {
        return qos;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    // Auto-configuration getters & setters

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

}