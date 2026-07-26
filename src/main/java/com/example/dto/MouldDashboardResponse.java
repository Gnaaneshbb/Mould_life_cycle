package com.example.dto;

import com.example.enums.MouldStatus;

public class MouldDashboardResponse {

    private String mouldId;
    private int totalCycles;
    private MouldStatus status;

    private int nextInspectionAt;
    private int remainingForInspection;

    public MouldDashboardResponse(
            String mouldId,
            int totalCycles,
            MouldStatus status,
            int nextInspectionAt
    ) {
        this.mouldId = mouldId;
        this.totalCycles = totalCycles;
        this.status = status;
        this.nextInspectionAt = nextInspectionAt;
        this.remainingForInspection = nextInspectionAt - totalCycles;
    }

    public String getMouldId() { return mouldId; }
    public int getTotalCycles() { return totalCycles; }
    public MouldStatus getStatus() { return status; }
    public int getNextInspectionAt() { return nextInspectionAt; }
    public int getRemainingForInspection() { return remainingForInspection; }
}