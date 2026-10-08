package com.example.FraudDetection.service;

import com.example.FraudDetection.dto.UserRequestDTO;
import com.example.FraudDetection.dto.UserResponseDTO;
import com.example.FraudDetection.entity.User;
import com.example.FraudDetection.globalexception.UserNotFoundException;
import com.example.FraudDetection.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder= passwordEncoder;
    }

    public UserResponseDTO createUser(UserRequestDTO request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();

        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());
        response.setRole(savedUser.getRole());

        return response;
    }

    public List<UserResponseDTO> getUsers(){
        List<User> response = userRepository.findAll();
        return response.stream()
            .map(this::toResponseDTO).toList();

    }
    public UserResponseDTO getUserById(long id){
         User user = userRepository.findById(id)
        .orElseThrow(
            ()-> new UserNotFoundException("User not found")
        );
        return toResponseDTO(user);
    }
    public UserResponseDTO toResponseDTO(User user){
        UserResponseDTO response = new UserResponseDTO();
        response.setEmail(user.getEmail());
        response.setId(user.getId());
        response.setRole(user.getRole());
        response.setUsername(user.getUsername());
        return response;
    }
    public void deleteUserById(long id){
        User user = userRepository.findById(id)
        .orElseThrow(
            ()-> new UserNotFoundException("No user found to delete")
        );
        userRepository.delete(user);
    }
}