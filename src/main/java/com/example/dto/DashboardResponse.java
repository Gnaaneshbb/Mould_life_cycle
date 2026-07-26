package com.example.dto;

public class DashboardResponse {

    private String mouldId;
    private int totalCycles;
    private String status;
    private int nextInspectionAt;

    public DashboardResponse(String mouldId, int totalCycles, String status,
                              int nextInspectionAt) {
        this.mouldId = mouldId;
        this.totalCycles = totalCycles;
        this.status = status;
        this.nextInspectionAt = nextInspectionAt;
    }

    public String getMouldId() { return mouldId; }
    public int getTotalCycles() { return totalCycles; }
    public String getStatus() { return status; }
    public int getNextInspectionAt() { return nextInspectionAt; }
}