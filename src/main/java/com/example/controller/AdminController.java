package com.example.controller;

import com.example.entity.Mould;
import com.example.entity.Operator;
import com.example.enums.MouldStatus;
import com.example.repository.MouldRepository;
import com.example.repository.OperatorRepository;
import com.example.service.MouldService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final MouldService mouldService;
    private final OperatorRepository operatorRepository;
    private final MouldRepository mouldRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(
            MouldService mouldService,
            OperatorRepository operatorRepository,
            MouldRepository mouldRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.mouldService = mouldService;
        this.operatorRepository = operatorRepository;
        this.mouldRepository = mouldRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // =====================================
    // 1️⃣ ADD MOULD
    // =====================================
    @PostMapping("/mould")
    public Mould addMould(@RequestBody Mould mould) {

        return mouldService.createMould(mould);

    }

    // =====================================
    // 2️⃣ CREATE USER
    // =====================================
    @PostMapping("/user")
    public Operator createUser(@RequestBody Operator user) {

        // encrypt password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return operatorRepository.save(user);
    }

    // =====================================
    // 3️⃣ VIEW INSPECTION PENDING
    // =====================================
    @GetMapping("/inspection-pending")
    public List<Mould> getPendingInspection() {

        return mouldRepository.findByStatus(MouldStatus.INSPECTION_PENDING);

    }

    // =====================================
    // 4️⃣ VIEW INSPECTION COMPLETED
    // =====================================
    @GetMapping("/inspection-completed")
    public List<Mould> getCompletedInspection() {

        return mouldRepository.findByStatus(MouldStatus.ACTIVE);

    }

}