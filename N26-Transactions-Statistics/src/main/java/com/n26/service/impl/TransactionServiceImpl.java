package com.n26.service.impl;

import com.n26.methodstat.MethodStats;
import com.n26.model.Transaction;
import com.n26.service.IStatisticsService;
import com.n26.service.ITransactionService;
import com.n26.validator.TransactionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements ITransactionService {


    @Autowired
    private IStatisticsService statisticsService;
    
    @Autowired
    private TransactionValidator transactionValidator;

    @Override
    @MethodStats
    public void saveTransaction(Transaction tx) {
        transactionValidator.validateTransaction(tx);
        statisticsService.updateStatistics(tx);
    }

    @Override
    @MethodStats
    public void deleteAllTransactions() {
        statisticsService.removeAllStatistics();
    }

}
