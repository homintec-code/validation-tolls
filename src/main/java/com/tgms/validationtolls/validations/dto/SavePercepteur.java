package com.tgms.validationtolls.validations.dto;

public class SavePercepteur {

    private String nom;

    private  String prenom;

    private  Long site_id;


    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }


    public String getNom() {
        return nom;
    }

    public int getSite_id() {
        return Math.toIntExact(site_id);
    }
    public void setSite_id(int site_id) {
        this.site_id = (long) site_id;
    }


    public String getPrenom() {
        return prenom;
    }
}
