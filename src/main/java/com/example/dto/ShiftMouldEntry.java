package com.example.dto;

public class ShiftMouldEntry {

    private String mouldId;
    private int cycles;

    public ShiftMouldEntry(String mouldId, int cycles) {
        this.mouldId = mouldId;
        this.cycles = cycles;
    }

    public String getMouldId() { return mouldId; }
    public int getCycles() { return cycles; }
}