package com.tgms.validationtolls.validations.model;

import com.tgms.validationtolls.Audit;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "sites")
public class Sites extends Audit {

    @Column(nullable = false)
    private String nom;  // Name of the site

    @Column(nullable = false)
    private Double tarif;  // Tarif (price) associated with the site

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getTarif() {
        return this.tarif;
    }

    public void setTarif(Double tarif) {
        this.tarif = tarif;
    }
}
