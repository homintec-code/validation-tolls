package com.tgms.validationtolls.validations.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Infraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_debut")
    private Date dateDebut;

    @Column(name = "date_fin")
    private Date dateFin;

    @Column(name = "boucle_exit")
    private Boolean boucleExit;

    @Column(name = "alimentation_boucle")
    private Boolean alimentationBoucle;

    @ManyToOne
    @JoinColumn(name = "percepteur_id")
    private Percepteur percepteur;

    // Getters and Setters
}