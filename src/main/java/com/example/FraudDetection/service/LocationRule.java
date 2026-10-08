package com.example.FraudDetection.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.FraudDetection.dto.TransactionRequestDTO;
import com.example.FraudDetection.repository.TransactionRepository;

@Service
public class LocationRule implements FraudRule {
    private final TransactionRepository transactionRepository;
    public LocationRule(TransactionRepository transactionRepository){
        this.transactionRepository=transactionRepository;
    }

    @Override
    public int evaluateRiskScore(TransactionRequestDTO request, Long userId) {
    List<String> previousLocation = transactionRepository.findDistinctLocationByUserId(userId);
    if(previousLocation.isEmpty()){
        return 0;
    }
    if(!previousLocation.contains(request.getLocation())){
        return 20;
    }
    return 0;
    }
}