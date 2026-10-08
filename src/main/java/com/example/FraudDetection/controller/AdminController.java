package com.example.FraudDetection.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FraudDetection.dto.TransactionResponseDTO;
import com.example.FraudDetection.service.TransactionService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final TransactionService transactionService;

    public AdminController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/test")
    public String adminTest() {
        return "Welcome Admin";
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionResponseDTO>> getAllTransactions() {

        List<TransactionResponseDTO> transactions =
                transactionService.getAllTransactions();

        return ResponseEntity.ok(transactions);
    }
}