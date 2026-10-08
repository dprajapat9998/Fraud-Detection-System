package com.example.FraudDetection.globalexception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message){
         super(message);
    }
}
