package com.example.FraudDetection.controller;

import com.example.FraudDetection.dto.TransactionRequestDTO;
import com.example.FraudDetection.dto.TransactionResponseDTO;
import com.example.FraudDetection.service.TransactionService;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(
            @Valid @RequestBody TransactionRequestDTO request) {

        TransactionResponseDTO response =
                transactionService.createTransaction(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
    @GetMapping 
    public ResponseEntity<List<TransactionResponseDTO>> getMyTransactions(){
        List<TransactionResponseDTO> list = transactionService.getMyTransactions();
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponseDTO> getTransactionsById(@PathVariable long id){
       TransactionResponseDTO response = transactionService.getTransactionsById(id);
        return ResponseEntity
        .status(HttpStatus.OK)
        .body(response);
    }
}