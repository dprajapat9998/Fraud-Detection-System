package com.example.FraudDetection.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.FraudDetection.dto.TransactionRequestDTO;
@Service 
public class AmountRule implements FraudRule {

    @Override
    public int evaluateRiskScore(TransactionRequestDTO request, Long userId) {
        BigDecimal amount = request.getAmount();

        if (amount.compareTo(new BigDecimal("10000")) <= 0) {

            return 10;

        } else if (amount.compareTo(new BigDecimal("50000")) <= 0) {

            return 50;

        } else {
            return 90;
        }
    }
    
}
