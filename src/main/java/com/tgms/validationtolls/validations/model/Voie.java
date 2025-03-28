package com.tgms.validationtolls.validations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tgms.validationtolls.Audit;
import jakarta.persistence.*;

@Entity
@Table(name = "voies")
public class Voie extends Audit {

    private String nom;
    private String sens;
    private String type;
    private String ip;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "site_id", nullable = false)
    //@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private Sites site;

    // Getters and setters

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSens() {
        return sens;
    }

    public void setSens(String sens) {
        this.sens = sens;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Sites getSite() {
        return site;
    }

    public void setSite(Sites site) {
        this.site = site;
    }
}
