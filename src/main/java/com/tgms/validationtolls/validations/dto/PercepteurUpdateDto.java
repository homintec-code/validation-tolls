package com.tgms.validationtolls.validations.dto;

import lombok.Data;

@Data
public class PercepteurUpdateDto {

    private Long id;

    private String nom;

    private  String prenom;

    private  int codePercepteur;

    private boolean active;


}