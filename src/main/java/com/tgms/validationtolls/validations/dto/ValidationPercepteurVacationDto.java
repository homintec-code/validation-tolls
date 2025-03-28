package com.tgms.validationtolls.validations.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ValidationPercepteurVacationDto {
    private Integer percepteurId;
    private Integer voieId;
    private LocalDate dateStart;
    private LocalDate dateEnd;

    // Getters et setters
}

