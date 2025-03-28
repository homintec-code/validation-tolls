package com.tgms.validationtolls.validations.dto;

public class InfoPasswordResponse {
    private InfoPasswordRequest response;
    private int status;

    // Constructeur
    public InfoPasswordResponse(InfoPasswordRequest response, int status) {
        this.response = response;
        this.status = status;
    }

    // Getters et Setters
    public InfoPasswordRequest getResponse() {
        return response;
    }

    public void setResponse(InfoPasswordRequest response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}