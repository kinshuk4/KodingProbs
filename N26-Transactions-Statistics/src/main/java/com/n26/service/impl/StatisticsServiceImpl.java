package com.n26.service.impl;

import com.n26.cache.CacheFactory;
import com.n26.cache.IStatCache;
import com.n26.methodstat.MethodStats;
import com.n26.model.StatisticsSummary;
import com.n26.model.Transaction;
import com.n26.service.IStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StatisticsServiceImpl implements IStatisticsService {

    IStatCache cache = CacheFactory.getCache(CacheFactory.CacheType.Caffeine);
    @Override
    @MethodStats
    public void updateStatistics(Transaction tx) {
        cache.add(tx);
    }

    @Override
    @MethodStats
    public StatisticsSummary getStatistics() {
        return cache.getLast60SecondsStatistics();
    }


    @Override
    @MethodStats
    public void removeAllStatistics() {
        cache.clear();
    }

}
