package com.example.FraudDetection.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FraudResult {

    private Integer riskScore;
    private String status;

    public FraudResult(Integer riskScore, String status) {
        this.riskScore = riskScore;
        this.status = status;
    }
}