package com.tgms.validationtolls.validations.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InfractionDto {

    @JsonProperty("boucle_exit")
    private String boucleExit;

    @JsonProperty("alimentation_boucle")
    private String alimentationBoucle;

    @JsonProperty("percepteur_id")
    private String percepteurId;

    @JsonProperty("date_debut")
    private String dateDebut;

    @JsonProperty("date_fin")
    private String dateFin;


}