package com.tgms.validationtolls.validations.dto;

import com.tgms.validationtolls.validations.model.Voie;
import com.tgms.validationtolls.validations.repository.VacationShowProjection;
import lombok.Data;

@Data
public class VoieWithLoggers {
    private Voie voie;
    private VacationShowProjection vacation;
    private StatistiqueValidationDto validation;
    private boolean isValidated;

    // Constructor, getters, and setters
    public VoieWithLoggers(Voie voie, VacationShowProjection vacation, StatistiqueValidationDto validation, boolean isValidated) {
        this.voie = voie;
        this.vacation = vacation;
        this.validation = validation;
        this.isValidated = isValidated;
    }

}