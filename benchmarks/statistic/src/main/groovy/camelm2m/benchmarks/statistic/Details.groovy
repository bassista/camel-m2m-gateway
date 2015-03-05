package camelm2m.benchmarks.statistic

import static java.lang.management.ManagementFactory.getThreadMXBean

class Details {

    private final Long created;
    private final Long consumed;
    private final Long duration;
    private final Long creationPerformance;
    private final Long consumptionPerformance;

    Details(long created, Long consumed, Long duration, Long creationPerformance, Long consumptionPerformance) {
        this.created = created;
        this.consumed = consumed;
        this.duration = duration;
        this.creationPerformance = creationPerformance;
        this.consumptionPerformance = consumptionPerformance;
    }

    Long created() {
        created
    }

    Long consumed() {
        consumed
    }

    Long duration() {
        duration
    }

    Long creationPerformance() {
        creationPerformance
    }

    Long consumptionPerformance() {
        consumptionPerformance
    }

    Long getCurrentQueueSize() {
        created - consumed
    }

    Long threadCount() {
        getThreadMXBean().getThreadCount()
    }
}