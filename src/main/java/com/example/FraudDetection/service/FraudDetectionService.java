package com.example.FraudDetection.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.FraudDetection.dto.FraudResult;
import com.example.FraudDetection.dto.TransactionRequestDTO;

@Service
public class FraudDetectionService {

    private final List<FraudRule> fraudRules;

    public FraudDetectionService(List<FraudRule> fraudRules) {
        this.fraudRules = fraudRules;
    }

    public FraudResult detectFraud(TransactionRequestDTO request,Long userId) {

        int riskScore = 0;

        for (FraudRule rule : fraudRules) {
            riskScore += rule.evaluateRiskScore(request, userId);
        }
        riskScore = Math.min(riskScore, 100);

        if (riskScore <= 10) {
            return new FraudResult(riskScore, "APPROVED");

        } else if (riskScore <= 50) {
            return new FraudResult(riskScore, "UNDER_REVIEW");

        } else {
            return new FraudResult(riskScore, "BLOCKED");
        }
    }
}