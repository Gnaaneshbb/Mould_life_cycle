package com.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "shift_entries")
public class ShiftEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String operatorUsername;

    private String mouldId;

    private LocalDate date;

    private String shift;

    private int enteredCycles;

    private LocalDateTime timestamp;

    // Getters and Setters
    public Long getId() { return id; }

    public String getOperatorUsername() { return operatorUsername; }
    public void setOperatorUsername(String operatorUsername) { this.operatorUsername = operatorUsername; }

    public String getMouldId() { return mouldId; }
    public void setMouldId(String mouldId) { this.mouldId = mouldId; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }

    public int getEnteredCycles() { return enteredCycles; }
    public void setEnteredCycles(int enteredCycles) { this.enteredCycles = enteredCycles; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}