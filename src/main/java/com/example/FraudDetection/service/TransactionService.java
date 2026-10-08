package com.example.FraudDetection.service;

import com.example.FraudDetection.dto.FraudResult;
import com.example.FraudDetection.dto.TransactionRequestDTO;
import com.example.FraudDetection.dto.TransactionResponseDTO;
import com.example.FraudDetection.entity.Transaction;
import com.example.FraudDetection.entity.User;
import com.example.FraudDetection.globalexception.ForbiddenException;
import com.example.FraudDetection.globalexception.TransactionNotFoundException;
import com.example.FraudDetection.globalexception.UserNotFoundException;
import com.example.FraudDetection.repository.TransactionRepository;
import com.example.FraudDetection.repository.UserRepository;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final FraudDetectionService fraudDetectionService;

    public TransactionService(TransactionRepository transactionRepository,
            UserRepository userRepository,
            FraudDetectionService fraudDetectionService) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.fraudDetectionService = fraudDetectionService;
    }

    public TransactionResponseDTO createTransaction(
            TransactionRequestDTO request) {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        FraudResult result = fraudDetectionService.detectFraud(
                request,
                user.getId());

        Transaction transaction = new Transaction();

        transaction.setAmount(request.getAmount());
        transaction.setCurrency(request.getCurrency());
        transaction.setMerchant(request.getMerchant());
        transaction.setLocation(request.getLocation());
        transaction.setDeviceId(request.getDeviceId());

        transaction.setUser(user);

        transaction.setStatus(result.getStatus());
        transaction.setRiskScore(result.getRiskScore());

        Transaction savedTransaction = transactionRepository.save(transaction);

        return toResponseDTO(savedTransaction);
    }

    public List<TransactionResponseDTO> getAllTransactions() {
        List<Transaction> transaction = transactionRepository.findAll();
        return transaction.stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public TransactionResponseDTO getTransactionsById(long id) {

        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(
                        "Transaction with " + id + " not found"));

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        boolean isAdmin = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin &&
                !transaction.getUser().getUsername().equals(username)) {

            throw new ForbiddenException(
                    "You are not allowed to access this transaction");
        }

        return toResponseDTO(transaction);
    }

    private TransactionResponseDTO toResponseDTO(
            Transaction transaction) {

        TransactionResponseDTO response = new TransactionResponseDTO();

        response.setId(transaction.getId());
        response.setAmount(transaction.getAmount());
        response.setCurrency(transaction.getCurrency());
        response.setMerchant(transaction.getMerchant());
        response.setLocation(transaction.getLocation());
        response.setDeviceId(transaction.getDeviceId());
        response.setStatus(transaction.getStatus());
        response.setRiskScore(transaction.getRiskScore());
        response.setUserId(transaction.getUser().getId());

        return response;
    }

    public List<TransactionResponseDTO> getMyTransactions() {

        String username = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return transactionRepository.findByUserId(user.getId())
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

}