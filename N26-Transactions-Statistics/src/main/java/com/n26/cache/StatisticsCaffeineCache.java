package com.n26.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.n26.model.Statistics;
import com.n26.model.StatisticsSummary;
import com.n26.model.Transaction;
import com.n26.utils.BigDecimalUtils;
import com.n26.utils.DateUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

import static com.n26.constant.Constants.STATS_AGGREGATION_TIME_MILLIS;
import static com.n26.utils.ModelTransformerUtils.addTxToStatistics;
import static com.n26.utils.ModelTransformerUtils.convertTxToStatistics;

public class StatisticsCaffeineCache implements IStatCache{
    private StatisticsCaffeineCache(){}
    private static class SingletonHelper{
        private static final StatisticsCaffeineCache INSTANCE = new StatisticsCaffeineCache();
    }


    public static StatisticsCaffeineCache getInstance() {
        return SingletonHelper.INSTANCE;
    }

    Cache<Integer, Statistics> cache = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES)
            .build();


    @Override
    public boolean add(Transaction tx) {
        Long transactionTimeInMillis = DateUtils.getEpocTime(tx.getTimestamp());
        Long currTimeMillis = System.currentTimeMillis();
        if ((currTimeMillis - transactionTimeInMillis) < STATS_AGGREGATION_TIME_MILLIS) {
            int second = tx.getTimestamp().getSecond();
            cache.asMap().compute(second, (key, value) -> {
                if (value == null || (System.currentTimeMillis() - value.getTimestamp()) >= STATS_AGGREGATION_TIME_MILLIS) {
                    return convertTxToStatistics(tx, transactionTimeInMillis);
                }
                return addTxToStatistics(value, tx);

            });
            return true;
        } else {
            return false;
        }
    }

    @Override
    public StatisticsSummary getLast60SecondsStatistics() {
        StatisticsSummary summary = cache.asMap().values().stream()
                .filter(s -> (System.currentTimeMillis() - s.getTimestamp()) < STATS_AGGREGATION_TIME_MILLIS)
                .map(new StatisticsSummary()::fromStatistics).reduce(new StatisticsSummary(), (s1, s2) -> {
                    s1.setSum(s1.getSum().add(s2.getSum()));
                    s1.setCount(s1.getCount().add(s2.getCount()));
                    s1.setMax(BigDecimalUtils.max(s1.getMax(), s2.getMax()));
                    s1.setMin(BigDecimalUtils.min(s1.getMin(), s2.getMin()));
                    return s1;
                });
        summary.setMin(BigDecimalUtils.min(summary.getMin(), null));
        summary.setMax(BigDecimalUtils.max(summary.getMax(), null));
        summary.setAvg(summary.getCount().compareTo(BigDecimal.ZERO) > 0
                ? summary.getSum().divide(summary.getCount(), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO);

        return summary;
    }

    @Override
    public int size(){
        return (int) cache.estimatedSize();
    }

    @Override
    public void clear() {
        cache.invalidateAll();
    }

}
