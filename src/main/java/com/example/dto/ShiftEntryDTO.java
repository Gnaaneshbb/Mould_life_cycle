package com.example.dto;

import java.time.LocalDate;

public class ShiftEntryDTO {

    private LocalDate date;
    private String shift;
    private String operator;
    private int enteredCycles;

    public ShiftEntryDTO(
            LocalDate date,
            String shift,
            String operator,
            int enteredCycles
    ) {
        this.date = date;
        this.shift = shift;
        this.operator = operator;
        this.enteredCycles = enteredCycles;
    }

    public LocalDate getDate() { return date; }
    public String getShift() { return shift; }
    public String getOperator() { return operator; }
    public int getEnteredCycles() { return enteredCycles; }
}