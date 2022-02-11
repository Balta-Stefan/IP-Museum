package com.virtualbank.advices;

import com.virtualbank.exceptions.InsufficientFunds;
import com.virtualbank.exceptions.NotFoundException;
import com.virtualbank.exceptions.TransactionAlreadyDone;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound(){}

    @ExceptionHandler(InsufficientFunds.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleInsufficientFunds(){}

    @ExceptionHandler(TransactionAlreadyDone.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleAlreadyFinishedTransaction(){}

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public void handleSQLViolation(){}

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleInternalExceptions(){}
}
