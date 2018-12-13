package com.n26.service.impl;

import com.n26.exception.FutureTransactionError;
import com.n26.exception.StaleTransactionError;
import com.n26.model.Transaction;
import com.n26.service.IStatisticsService;
import com.n26.validator.TransactionValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class TransactionServiceImplTest {

    @Mock
    private TransactionValidator transactionValidator;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private IStatisticsService statisticsService;

    @Before
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = FutureTransactionError.class)
    public void future_transaction_should_throw_error() {
        Mockito.doCallRealMethod().when(transactionValidator).validateTransaction(Mockito.any(Transaction.class));
        transactionService.saveTransaction(new Transaction(new BigDecimal(120), LocalDateTime.now().plusSeconds(30)));
    }

    @Test(expected = StaleTransactionError.class)
    public void stale_transaction_should_throw_error() {
        Mockito.doCallRealMethod().when(transactionValidator).validateTransaction(Mockito.any(Transaction.class));
        transactionService.saveTransaction(new Transaction(new BigDecimal(120), LocalDateTime.now().minusMinutes(4)));
    }

    @Test
    public void valid_transaction_request_should_pass_through() {
        Mockito.doCallRealMethod().when(transactionValidator).validateTransaction(Mockito.any(Transaction.class));
        transactionService.saveTransaction(new Transaction(new BigDecimal(120), LocalDateTime.now().minusSeconds(30)));
        
    }

}
