package com.tgms.validationtolls.validations.model;

import com.tgms.validationtolls.Audit;
import com.tgms.validationtolls.validations.enums.LogingStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "logs")
@Data
public class Logs extends Audit {

    @Column(name = "date", nullable = true)
    private Date date;

    @Column(name = "heure", nullable = true)
    private LocalTime heure;

    @Column(name = "is_sent", nullable = false)
    private boolean isSent = false;

    @Column(name = "refer", nullable = false)
    private String refer;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private LogingStatus statut = LogingStatus.LOGGER;

    @Column(name = "is_cabin")
    private boolean isCabin = false;

    @Column(name = "is_start")
    private boolean isStart = false;

    @Column(name = "cabin_at", nullable = false)
    private String cabinAt;

    @Column(name = "start_at", nullable = true)
    private String startAt;

    @Column(name = "deconnected_at", nullable = true)
    private String deconnectedAt;

    @Column(name = "is_deconnected")
    private boolean isDeconnected = false;

    @Column(name = "accountability")
    private boolean accountability = false;

    @Column(name = "accountability_at", nullable = true)
    private String accountabilityAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "percepteur_id", referencedColumnName = "id", nullable = true)
    private Percepteur percepteur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voie_id", referencedColumnName = "id", nullable = true)
    private Voie voie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "old_percepteur_id", referencedColumnName = "id", nullable = true)
    private Percepteur oldPercepteur;



}
