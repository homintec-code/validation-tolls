package com.tgms.validationtolls.validations.dto;

import lombok.Data;

@Data
public class StatistiqueValidationDto {
    private String dateStart;
    private String dateEnd;
    private PercepteurDTO percepteur;
    private VoieDto voie;
    private String site;
    private Double montantCaisse;
    private Double montant;
    private Double montantPenalite;
    private Integer nbrePenalite;
    private Integer violation;
    private Integer nbreVehicule;
    private Integer nbreGateSucces;
    private Double montantGate;
    private Integer nbreGateInvalid;
    private Integer nbreGateInsuf;
    private Integer nbrePl;
    private Double pdTotalTransport;
    private String typeVehicule;
    private Integer es2;
    private Integer es3;
    private Integer es4;
    private Integer es5;
    private Integer es6;
    private Integer es7;
    private Integer es8;
    private Integer es9;
    private Integer vl;
    private Integer moto;
    private Integer tricycle;
    private Integer minibus;
    private Integer autobus;
    private Integer pl;
    private Integer exo;
    private Double montantExo;


}