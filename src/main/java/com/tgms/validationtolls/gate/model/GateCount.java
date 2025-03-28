package com.tgms.validationtolls.gate.model;

import com.tgms.validationtolls.Audit;
import com.tgms.validationtolls.validations.model.Percepteur;
import com.tgms.validationtolls.validations.model.Vacation;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "gate_count")
@Getter
@Setter
public class GateCount extends Audit {

    @Column(name = "invalid", nullable = false, columnDefinition = "boolean default false")
    private boolean invalid;

    @Column(name = "insuf", nullable = false, columnDefinition = "boolean default false")
    private boolean insuf;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "percepteur_id", nullable = false)
    private Percepteur percepteur;

    @ManyToOne
    @JoinColumn(name = "vacation_id", nullable = true)
    private Vacation vacation;
}
