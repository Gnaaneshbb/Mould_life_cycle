package com.example.entity;

import com.example.enums.MouldStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "moulds")
public class Mould {

    @Id
    private String mouldId;
    
    private String mouldSize;

    private String mouldNumber;
    
    private Integer outerDia;
    private Integer innerDia;
    private String type;

    private int totalCycles;

    private int nextInspectionAt;

    @Enumerated(EnumType.STRING)
    private MouldStatus status;

    public Mould() {}

    public Mould(String mouldId) {
        this.mouldId = mouldId;
        this.totalCycles = 0;
   
        this.nextInspectionAt = 8000;
        this.status = MouldStatus.ACTIVE;
    }

    // getters & setters

    public String getMouldId() { return mouldId; }
    public int getTotalCycles() { return totalCycles; }
    public void setTotalCycles(int totalCycles) { this.totalCycles = totalCycles; }

 
    public int getNextInspectionAt() { return nextInspectionAt; }
    public void setNextInspectionAt(int nextInspectionAt) { this.nextInspectionAt = nextInspectionAt; }

    public MouldStatus getStatus() { return status; }
    public void setStatus(MouldStatus status) { this.status = status; }
    
    
    public Integer getOuterDia() {
        return outerDia;
    }

    public void setOuterDia(Integer outerDia) {
        this.outerDia = outerDia;
    }

    public Integer getInnerDia() {
        return innerDia;
    }

    public void setInnerDia(Integer innerDia) {
        this.innerDia = innerDia;
    }

    public String getMouldSize() {
        return mouldSize;
    }

    public void setMouldSize(String mouldSize) {
        this.mouldSize = mouldSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getMouldNumber() {
        return mouldNumber;
    }

    public void setMouldNumber(String mouldNumber) {
        this.mouldNumber = mouldNumber;
    }
}
