package com.n26.exception.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.exception.FutureTransactionError;
import com.n26.exception.StaleTransactionError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class RestExceptionHandlerAdvice {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public void defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        log.error("Internal Server Error - " + req.getRequestURI(), e);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public void badRequestError(MethodArgumentNotValidException e) {
        log.error("Bad Request Error !", e);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ResponseBody
    @ExceptionHandler({StaleTransactionError.class})
    public void staleRequestError(StaleTransactionError e) {
        log.error("InvalidRequestError !", e);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler({FutureTransactionError.class})
    public void invalidRequestError(FutureTransactionError e) {
        log.error("InvalidRequestError !", e);
    }

    @ResponseBody
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<Object> badRequest(HttpMessageNotReadableException e) {
        log.error("invalidRequestError !", e);
        if (e.getCause() instanceof InvalidFormatException) {
            return new ResponseEntity<>(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



}
