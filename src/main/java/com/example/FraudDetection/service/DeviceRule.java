package com.example.FraudDetection.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FraudDetection.dto.TransactionRequestDTO;
import com.example.FraudDetection.repository.TransactionRepository;

@Service 
public class DeviceRule implements FraudRule {
    final private TransactionRepository transactionRepository;
    public DeviceRule(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }
    @Override
    public int evaluateRiskScore(TransactionRequestDTO request, Long userId) {
          List<String> previousDevices = transactionRepository.
          findDistinctDeviceIdByUserId(userId);
          if(previousDevices.isEmpty()) return 0;

          if(!previousDevices.contains(request.getDeviceId())){
             return 20;
          }

          return 0;

    }

}
