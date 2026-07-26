package com.example.controller;

import com.example.dto.DashboardResponse;
import com.example.dto.MouldDashboardResponse;
import com.example.dto.ProductionRequest;
import com.example.dto.ShiftSummaryResponse;
import com.example.service.MouldService;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/production")
public class ProductionController {
	

    private final MouldService mouldService;
    

    public ProductionController(MouldService mouldService) {
        this.mouldService = mouldService;
    }

    @PostMapping("/submit")
    public DashboardResponse submit(
            @RequestBody ProductionRequest request,
            Authentication authentication) {
    	
    	System.out.println("Authentication object: " + authentication);

        String username = authentication.getName();

        return mouldService.submitProduction(username, request);
    }
    
    
    @GetMapping("/shift-summary")
    public ShiftSummaryResponse getShiftSummary(
            @RequestParam String date,
            @RequestParam String shift
    ) {
        return mouldService.getShiftSummary(
                LocalDate.parse(date),
                shift
        );
    }
    
    
    @GetMapping("/mould-dashboard")
    public MouldDashboardResponse getMouldDashboard(
            @RequestParam String mouldId
    ) {
        return mouldService.getMouldDashboard(mouldId);
    }
    
    
}