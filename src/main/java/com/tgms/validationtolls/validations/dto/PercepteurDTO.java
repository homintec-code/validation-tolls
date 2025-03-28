package com.tgms.validationtolls.validations.dto;

import lombok.Data;

@Data
public  class PercepteurDTO {
    private Long id;
    private String nom;
    private String prenom;

    public PercepteurDTO(Long id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

}