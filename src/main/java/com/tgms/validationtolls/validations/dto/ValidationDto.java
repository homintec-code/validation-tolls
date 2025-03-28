package com.tgms.validationtolls.validations.dto;

import com.tgms.validationtolls.validations.model.Sites;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;

@Data
public class ValidationDto {

    private String prix;

    private String type;

    private String ptrac;

    private String cmaes;

    private Integer es;

    private String essieuCapter;

    private String essieuCorriger;

    private String ptt;

    private String over;

    private String plaque;

    private String visa;

    private String refer;

    private Integer isViolation;

    private String isValid;

    private String isLoop;

    private String classe;

    private String isGate;

    private String isExo;

    private String plaquePercepteur;

    private String nomenclature;

    private String voieId;

    private String percepteurId;

    private String caisse;

    private String societe;
    private String ticketNumber;
    private String vacationId;

    @Setter
    public static class PercepteurDTO {
        private Long id;
        private String nom;
        private String prenom;
        private int codePercepteur;
        private ValidationVacationDto.SiteDTO site; // DTO pour Site

        public PercepteurDTO(Long id, String nom, String prenom, int codePercepteur, Sites site) {
        }
    }
}