package com.tgms.validationtolls.validations.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tgms.validationtolls.Audit;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "validation")
@Data
public class Validation  extends Audit {

    @Column(nullable = false)
    private String heure;

    @Column(nullable = true)
    private LocalDate date;

    @Column(name = "date_api",nullable = true)
    private LocalDateTime dateApi;

    @Column(nullable = true)
    private String sens;

    @Column(nullable = true)
    private Double prix;

    @Column(nullable = true)
    private String type;

    @Column(nullable = true)
    private String ptrac;

    @Column(nullable = false)
    private String cmaes;

    @Column(nullable = false)
    private int es;

    @Column(name = "essieu_capter", nullable = true)
    private Integer essieuCapter;

    @Column(name = "essieu_corriger",nullable = true)
    private Integer essieuCorriger;

    @Column(nullable = true)
    private String ptt;

    @Column(nullable = true)
    private String over;

    @Column(nullable = false)
    private int caisse;

    @Column(nullable = true)
    private String plaque;

    @Column(nullable = true)
    private String visa;

    @Column(nullable = true)
    private Double penalite;

    @Column(nullable = false, unique = true)
    private String refer;

    @Column( name = "is_violation", updatable = false)
    @Setter
    private boolean isViolation = true;

    @Column( name = "is_valid")
    private int isValid = 1;

    @Column( name = "is_loop" )
    private int isLoop = 0;

    @Column(nullable = true)
    private String classe;

    @Column(name = "is_gate")
    private boolean isGate = false;

    @Column(name = "is_exo")
    private boolean isExo = false;

    @Column(name = "plaque_percepteur", nullable = true)
    private String plaquePercepteur;

    @Column(name = "ticket_number", nullable = true)
    private String ticketNumber;

    @Column(nullable = true)
    private String societe;

    @Column(nullable = false)
    private String nomenclature;


    @Column(nullable = true,name = "percepteur_id")
    private int percepteurId;


    @Column(nullable = true,name = "voie_id")
    private int voieId;

    @Column(name = "vacation_id", nullable = false)
    private int vacationId;


    private boolean synced = false;
    private LocalDateTime lastSyncAttempt;
    private int syncAttemptCount;

    public void setGate(boolean gate) {
        isGate = gate;
    }

    public boolean isExo() {
        return isExo;
    }

    public void setExo(boolean exo) {
        isExo = exo;
    }

    // Getters and Setters
    // ...
}
