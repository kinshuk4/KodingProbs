package com.n26.service;

import com.n26.model.Transaction;

public interface ITransactionService {
    void saveTransaction(Transaction tx);
    void deleteAllTransactions();
}
