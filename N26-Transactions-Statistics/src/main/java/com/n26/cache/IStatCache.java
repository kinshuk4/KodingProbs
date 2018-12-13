package com.n26.cache;

import com.n26.model.StatisticsSummary;
import com.n26.model.Transaction;

public interface IStatCache{

        boolean add(Transaction tx);

        int size();

        void clear();

        StatisticsSummary getLast60SecondsStatistics();
}
