package com.n26.validator;

import com.n26.exception.FutureTransactionError;
import com.n26.exception.StaleTransactionError;
import com.n26.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransactionValidator {

    public void validateTransaction(Transaction tx) {
        if (tx.getTimestamp().isBefore(LocalDateTime.now().minusSeconds(60))) {
            throw new StaleTransactionError();
        }
        if (tx.getTimestamp().isAfter(LocalDateTime.now())) {
            throw new FutureTransactionError();
        }

    }

}
