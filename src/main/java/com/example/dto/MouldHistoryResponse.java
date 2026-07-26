package com.example.dto;

import com.example.enums.MouldStatus;
import java.util.List;

public class MouldHistoryResponse {

    private String mouldId;
    private int totalCycles;
    private MouldStatus status;
    private List<ShiftEntryDTO> history;

    public MouldHistoryResponse(
            String mouldId,
            int totalCycles,
            MouldStatus status,
            List<ShiftEntryDTO> history
    ) {
        this.mouldId = mouldId;
        this.totalCycles = totalCycles;
        this.status = status;
        this.history = history;
    }

    public String getMouldId() { return mouldId; }
    public int getTotalCycles() { return totalCycles; }
    public MouldStatus getStatus() { return status; }
    public List<ShiftEntryDTO> getHistory() { return history; }
}