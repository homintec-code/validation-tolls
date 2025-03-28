package com.tgms.validationtolls.validations.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ValidationVacationDto {
    private Long vacationId;

    public static class SiteDTO {
        private Long id;
        private String nom;
        public Double tarif;

        public void setId(Long id) {
                this.id = id;
        }
        public Long getId() {
            return id;
        }
        public void setNom(String nom) {
            this.nom = nom;
        }
        public String getNom() {
            return nom;
        }
        public void setTarif(Double tarif) {
            this.tarif = tarif;
        }
        public Double getTarif() {

            return tarif;
        }

    }
}