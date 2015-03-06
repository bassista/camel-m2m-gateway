package camelm2m.benchmarks.statistic

import static java.lang.management.ManagementFactory.getThreadMXBean

class Details {

    private final Long created;
    private final Long consumed;
    private final Long duration;
    private final Long creationPerformance;
    private final Long consumptionPerformance;

    Long getCurrentQueueSize() {
        created - consumed
    }

    Long threadCount() {
        getThreadMXBean().getThreadCount()
    }
}