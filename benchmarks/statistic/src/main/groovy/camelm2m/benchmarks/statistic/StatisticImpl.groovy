package camelm2m.benchmarks.statistic

import org.apache.commons.lang3.time.StopWatch

import java.util.concurrent.atomic.AtomicLong

class StatisticImpl extends TimerTask implements Statistic {

    private final List<Details> list = new ArrayList<>();
    private final StopWatch stopWatch = new StopWatch();
    private final AtomicLong counterCreated = new AtomicLong(0L);
    private final AtomicLong counterConsumed = new AtomicLong(0L);

    StatisticImpl() {
        this(30);
    }

    StatisticImpl(int period) {
        Timer timer = new Timer();
        stopWatch.start();
        timer.schedule(this, period * 1000, period * 1000);     // every x sec
    }

    @Override
    void run() {
        list.add(details())
    }

    @Override
    void updateCreated() {
        counterCreated.incrementAndGet()
    }

    @Override
    void updateConsumed() {
        counterConsumed.incrementAndGet()
    }

    @Override
    Details details() {
        Long sec = (stopWatch.getTime() / 1000)
        Long createdCount = counterCreated.get()
        Long consumedCount = counterConsumed.get()
        Long creationPerformance = createdCount / sec
        Long consumptionPerformance = consumedCount / sec
        new Details(created: createdCount,
                    consumed: consumedCount,
                    duration: sec,
                    creationPerformance: creationPerformance,
                    consumptionPerformance: consumptionPerformance)
    }

    @Override
    List<Details> list() {
        list
    }
}