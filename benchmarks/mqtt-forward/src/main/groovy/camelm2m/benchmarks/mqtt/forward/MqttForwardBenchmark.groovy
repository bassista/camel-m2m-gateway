package camelm2m.benchmarks.mqtt.forward

import camelm2m.benchmarks.statistic.Statistic
import camelm2m.benchmarks.statistic.StatisticImpl
import org.apache.camel.spring.boot.FatJarRouter
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

import static java.util.UUID.randomUUID

@SpringBootApplication
class MqttForwardBenchmark extends FatJarRouter {

    @Value('${statistics.save.period:1000}')
    private int savePeriod;

    @Value('${sensors.number:3}')
    private int sensorsNumber;

    @Value('${sensors.mock.period:1}')
    private int period;

    @Value('${broker.consumers:5}')
    private int consumers;

    @Value('${queue.type:seda}')
    private String queueType;

    @Value('${max.thread.pool:100}')
    private int maxThreadPool;

    @Value('${brokerUrl:tcp://localhost:1883}')
    private String brokerUrl;

    @Override
    void configure() {
        errorHandler(deadLetterChannel("seda:DLQ"))
        context.getShutdownStrategy().setTimeout(5)

        for (int i = 0; i < sensorsNumber; i++) {
            from("timer://foo?period=${period}")
                    .threads(1, maxThreadPool)
                    .setBody().expression { randomUUID().toString() }
            .multicast()
                    .to("bean:statistic?method=updateCreated", "${queueType}://queue:RPi")
        }

        from("${queueType}://queue:RPi?concurrentConsumers=${consumers}")
                .multicast().to("paho:topic?brokerUrl=${brokerUrl}", "bean:statistic?method=updateConsumed")
    }

    @Bean
    Statistic statistic() {
        new StatisticImpl(savePeriod)
    }
}