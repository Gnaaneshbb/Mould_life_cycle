package com.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "deleted_moulds")
public class DeletedMould {

    @Id
    private String mouldId;

    private String mouldNumber;
    private String mouldSize;

    private Integer outerDia;
    private Integer innerDia;

    private String type;

    private Integer totalCycles;

    public String getMouldId() {
        return mouldId;
    }

    public void setMouldId(String mouldId) {
        this.mouldId = mouldId;
    }

    public String getMouldNumber() {
        return mouldNumber;
    }

    public void setMouldNumber(String mouldNumber) {
        this.mouldNumber = mouldNumber;
    }

    public String getMouldSize() {
        return mouldSize;
    }

    public void setMouldSize(String mouldSize) {
        this.mouldSize = mouldSize;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotalCycles() {
        return totalCycles;
    }

    public void setTotalCycles(Integer totalCycles) {
        this.totalCycles = totalCycles;
    }
}