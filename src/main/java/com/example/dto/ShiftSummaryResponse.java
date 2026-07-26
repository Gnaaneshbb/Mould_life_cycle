package com.example.dto;

import java.time.LocalDate;
import java.util.List;

public class ShiftSummaryResponse {

    private LocalDate date;
    private String shift;
    private int totalCyclesProduced;
    private int mouldsUsed;
    private List<ShiftMouldEntry> entries;

    public ShiftSummaryResponse(
            LocalDate date,
            String shift,
            int totalCyclesProduced,
            int mouldsUsed,
            List<ShiftMouldEntry> entries
    ) {
        this.date = date;
        this.shift = shift;
        this.totalCyclesProduced = totalCyclesProduced;
        this.mouldsUsed = mouldsUsed;
        this.entries = entries;
    }

    public LocalDate getDate() { return date; }
    public String getShift() { return shift; }
    public int getTotalCyclesProduced() { return totalCyclesProduced; }
    public int getMouldsUsed() { return mouldsUsed; }
    public List<ShiftMouldEntry> getEntries() { return entries; }
}