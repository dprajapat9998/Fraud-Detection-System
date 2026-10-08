package com.example.FraudDetection.controller;

import com.example.FraudDetection.dto.LoginRequestDTO;
import com.example.FraudDetection.dto.LoginResponseDTO;
import com.example.FraudDetection.service.AuthService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody LoginRequestDTO request) {

        String token = authService.login(request);

        return ResponseEntity.ok(
                new LoginResponseDTO(token)
        );
    }
}