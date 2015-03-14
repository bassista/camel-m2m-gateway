package camelm2m.quickstarts.mqttproducer

import org.apache.camel.spring.boot.FatJarRouter
import org.springframework.boot.autoconfigure.SpringBootApplication

import static java.util.UUID.randomUUID

@SpringBootApplication
class MqttProducerGateway extends FatJarRouter {

    @Override
    void configure() {
        // Read events from the sensors
        from("timer://mockSensor?period=10").
                setBody().expression { randomUUID().toString() }.
                to("seda://events") // Enqueue the events in the in-memory queue

        from("seda://events?concurrentConsumers={{broker.consumers:15}}").
                to("seda:done")
    }

}