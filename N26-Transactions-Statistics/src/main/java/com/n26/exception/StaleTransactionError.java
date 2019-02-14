package com.n26.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class StaleTransactionError extends RuntimeException {
    private static final long serialVersionUID = 2L;
}
