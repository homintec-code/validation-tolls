package com.tgms.validationtolls.validations.dto;

import com.tgms.validationtolls.validations.model.Percepteur;

public class InfoPasswordRequest {
    private Percepteur percepteur;
    private int password;



    // Getters et Setters
    public Percepteur getPercepteur() {
        return percepteur;
    }

    public void setPercepteur(Percepteur percepteur) {
        this.percepteur = percepteur;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }
}