package com.example.FraudDetection.service;

import com.example.FraudDetection.dto.TransactionRequestDTO;

public interface FraudRule {
     int evaluateRiskScore(TransactionRequestDTO request, Long userId);
}
