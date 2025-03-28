package com.tgms.validationtolls.validations.dto;

import lombok.Data;

@Data
public class LoginPercepteurDto {
    private Long percepteurId;
    private Long voieId;
    private String password;
    private Long vacationId;

    // Getters and Setters
}
