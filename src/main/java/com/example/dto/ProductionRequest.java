package com.example.dto;

import java.time.LocalDate;

public class ProductionRequest {

    private String mouldId;
    private LocalDate date;
    private String shift;
    private int enteredCycles;

    public String getMouldId() { return mouldId; }
    public void setMouldId(String mouldId) { this.mouldId = mouldId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public int getEnteredCycles() { return enteredCycles; }
    public void setEnteredCycles(int enteredCycles) { this.enteredCycles = enteredCycles; }
}