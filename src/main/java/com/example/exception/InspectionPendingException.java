package com.example.exception;

public class InspectionPendingException extends RuntimeException {

    public InspectionPendingException(String message) {
        super(message);
    }
}