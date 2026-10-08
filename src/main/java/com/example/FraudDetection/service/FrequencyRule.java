package com.example.FraudDetection.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.FraudDetection.dto.TransactionRequestDTO;
import com.example.FraudDetection.repository.TransactionRepository;

@Service
public class FrequencyRule implements FraudRule {

    private final TransactionRepository transactionRepository;

    public FrequencyRule(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public int evaluateRiskScore(TransactionRequestDTO request, Long userId) {

        LocalDateTime oneMinuteAgo =
                LocalDateTime.now().minusMinutes(1);

        long transactionCount =
                transactionRepository.countRecentTransactions(
                        userId,
                        oneMinuteAgo
                );

        if (transactionCount >= 5) {
            return 30;
        }

        return 0;
    }

}