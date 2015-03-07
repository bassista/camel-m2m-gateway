package camelm2m.benchmarks.statistic

import static java.lang.management.ManagementFactory.getThreadMXBean

class Details {

    Long created;
    Long consumed;
    Long duration;
    Long creationPerformance;
    Long consumptionPerformance;

    Long getCurrentQueueSize() {
        created - consumed
    }

    Long getThreadCount() {
        getThreadMXBean().getThreadCount()
    }
}