package camelm2m.benchmarks.mqtt.forward;

import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class MqttForwardBenchmark extends FatJarRouter {

    @Override
    public void configure() throws Exception {
        from("timer:foo").
                setBody().simple(UUID.randomUUID().toString()).
                to("paho:topic");
    }

}