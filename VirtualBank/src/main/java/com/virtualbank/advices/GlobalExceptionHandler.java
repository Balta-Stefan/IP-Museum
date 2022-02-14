package com.virtualbank.advices;

import com.virtualbank.exceptions.InsufficientFunds;
import com.virtualbank.exceptions.NotFoundException;
import com.virtualbank.exceptions.TransactionAlreadyDone;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleBeanValidationExceptions(MethodArgumentNotValidException exc)
    {
        Map<String, String> errors = new HashMap<>();
        exc.getBindingResult().getAllErrors().forEach((err) ->
        {
            String fieldName = ((FieldError)err).getField();
            String errorMsg = err.getDefaultMessage();
            errors.put(fieldName, errorMsg);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }


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
