package camelm2m.benchmarks.mqtt.forward

import org.apache.camel.spring.boot.FatJarRouter
import org.springframework.boot.autoconfigure.SpringBootApplication

import static java.util.UUID.randomUUID

@SpringBootApplication
class MqttForwardBenchmark extends FatJarRouter {

    @Override
    public void configure() throws Exception {
        from("timer:foo").
                setBody().expression { randomUUID().toString() }.
                to("paho:topic");
    }

}