package com.tgms.validationtolls.validations.dto;

public class CloseVacationDto {

    private Long percepteur_id;

    public Long getPercepteurId() {
        return percepteur_id;
    }
    public void setPercepteurId(String percepteur_id) {
        this.percepteur_id = Long.valueOf(percepteur_id);
    }
}

