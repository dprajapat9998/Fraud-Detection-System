package com.example.FraudDetection.globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return errors;
    }
    @ExceptionHandler(UserNotFoundException.class)
        public ResponseEntity<String> userNotfoundExeption(UserNotFoundException ex){
       
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
    }

    @ExceptionHandler(TransactionNotFoundException.class)
        public ResponseEntity<String> transactionNotFoundException(TransactionNotFoundException ex){
       
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(ex.getMessage());
    }
    @ExceptionHandler(ForbiddenException.class)
public ResponseEntity<String> handleForbiddenException(
        ForbiddenException ex) {

    return ResponseEntity
            .status(HttpStatus.FORBIDDEN)
            .body(ex.getMessage());
}
}