package com.example.FraudDetection.globalexception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
         super(message);
    }
}
