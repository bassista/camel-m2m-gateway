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

    @Value('${statistics.save.period:30}')
    private int savePeriod;

    @Override
    void configure() {
        from('timer:foo').
                setBody().expression { randomUUID().toString() }
                .multicast().to('seda:queue', 'bean:statistic?method=updateCreated')

        from('seda:queue').multicast().to("paho:topic", 'bean:statistic?method=updateConsumed')
    }

    @Bean
    Statistic statistic() {
        new StatisticImpl(savePeriod)
    }
}