package com.example.controller;

import com.example.dto.LoginRequest;
import com.example.entity.Operator;
import com.example.repository.OperatorRepository;
import com.example.security.JwtUtil;

import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final OperatorRepository operatorRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          OperatorRepository operatorRepository) {
        this.authenticationManager = authenticationManager;
        this.operatorRepository = operatorRepository;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getUsername(),
                                request.getPassword()
                        )
                );

        // 🔹 Fetch user from DB
        Operator operator = operatorRepository
                .findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 🔹 Use role from DB
        String token = JwtUtil.generateToken(
                operator.getUsername(),
                operator.getRole()
        );

        return Map.of("token", token);
    }
}