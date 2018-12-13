package com.n26.service;

import com.n26.model.StatisticsSummary;
import com.n26.model.Transaction;

public interface IStatisticsService {
    void updateStatistics(Transaction tx);
    StatisticsSummary getStatistics();
    void removeAllStatistics();
}
