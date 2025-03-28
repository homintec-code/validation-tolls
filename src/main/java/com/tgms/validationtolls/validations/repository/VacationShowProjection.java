package com.tgms.validationtolls.validations.repository;

public interface VacationShowProjection {
    Long getId();
    String getDateStart();
    String getDateEnd();
    String getDate();
    Boolean getIsClose();
    String getPercepteurNom();   // Nom du percepteur
    String getPercepteurPrenom(); // Prénom du percepteur
    Long getPercepteurId();
}
