package org.apache.camel.component.paho;

import org.apache.camel.Component;
import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.eclipse.paho.client.mqttv3.persist.MqttDefaultFilePersistence;

import static java.lang.System.nanoTime;
import static org.apache.camel.component.paho.PahoPersistence.MEMORY;

public class PahoEndpoint extends DefaultEndpoint {

    // Configuration members

    private String clientId = "camel-" + nanoTime();

    private String brokerUrl = "tcp://localhost:1883";

    private String topic;

    private int qos = 2;

    private PahoPersistence persistence = MEMORY;

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
        client = new MqttClient(getBrokerUrl(), getClientId(), resolvePersistence());
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        client.connect();
    }

    @Override
    protected void doStop() throws Exception {
        if (getClient().isConnected()) {
            getClient().disconnect();
        }
        super.doStop();
    }

    @Override
    public Producer createProducer() throws Exception {
        return new PahoProducer(this);
    }

    @Override
    public Consumer createConsumer(Processor processor) throws Exception {
        return new PahoConsumer(this, processor);
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public PahoComponent getComponent() {
        return (PahoComponent) super.getComponent();
    }

    // Resolvers

    protected MqttClientPersistence resolvePersistence() {
        return persistence == MEMORY ? new MemoryPersistence() : new MqttDefaultFilePersistence();
    }

    // Configuration getters & setters

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getBrokerUrl() {
        return brokerUrl;
    }

    public void setBrokerUrl(String brokerUrl) {
        this.brokerUrl = brokerUrl;
    }

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

    public PahoPersistence getPersistence() {
        return persistence;
    }

    public void setPersistence(PahoPersistence persistence) {
        this.persistence = persistence;
    }

    public MqttClient getClient() {
        return client;
    }

    public void setClient(MqttClient client) {
        this.client = client;
    }

}