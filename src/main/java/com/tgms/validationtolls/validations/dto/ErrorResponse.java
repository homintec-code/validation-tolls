package com.tgms.validationtolls.validations.dto;

public class ErrorResponse {
    private String message;

    // Constructor, getters, and setters
    public ErrorResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
