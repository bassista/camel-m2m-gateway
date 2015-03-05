package camelm2m.benchmarks.statistic

interface Statistic {
    void updateCreated();
    void updateConsumed();
    Details details();
    List<Details> list();
}