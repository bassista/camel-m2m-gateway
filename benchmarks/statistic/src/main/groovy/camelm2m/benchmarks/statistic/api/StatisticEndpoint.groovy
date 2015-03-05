package camelm2m.benchmarks.statistic.api

import camelm2m.benchmarks.statistic.Details
import org.apache.camel.builder.RouteBuilder
import org.springframework.beans.factory.annotation.Value

import static org.apache.camel.model.rest.RestBindingMode.json

class StatisticEndpoint extends RouteBuilder {

    @Value('${statistics.api.port:9900}')
    private int restPort;

    @Override
    void configure() throws Exception {
        restConfiguration().component("netty-http").
                host("0.0.0.0").port(restPort).bindingMode(json)

        rest("/statistic")
                .consumes("application/json").produces("application/json")

                .get("/details").outType(Details.class)
                .to("bean:statistic?method=details")

                .get("/list").outType(List.class)
                .to("bean:statistic?method=list")
    }
}