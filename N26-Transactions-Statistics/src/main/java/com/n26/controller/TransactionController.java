package com.n26.controller;

import com.n26.methodstat.MethodStats;
import com.n26.model.Transaction;
import com.n26.service.ITransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.n26.constant.Constants.ENDPOINT_TRANSACTIONS;

@RestController
@RequestMapping(ENDPOINT_TRANSACTIONS)
@Slf4j
public class TransactionController {

    @Autowired
    private ITransactionService transactionService;

    @PostMapping
    @MethodStats
    public ResponseEntity<Object> createTransaction(@Valid @NotNull @RequestBody Transaction transaction) {
        transactionService.saveTransaction(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @MethodStats
    public void deleteTransactions() {
        transactionService.deleteAllTransactions();
    }
}
