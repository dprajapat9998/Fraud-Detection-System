package com.example.FraudDetection.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransactionResponseDTO {

    private Long id;
    private BigDecimal amount;
    private String currency;
    private String merchant;
    private String location;
    private String deviceId;
    private String status;
    private Integer riskScore;
    private Long userId;
}