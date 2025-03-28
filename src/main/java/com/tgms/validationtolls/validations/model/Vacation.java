package com.tgms.validationtolls.validations.model;

import com.tgms.validationtolls.Audit;
import com.tgms.validationtolls.validations.enums.VacationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "vacations")
@Data
public class Vacation extends Audit {

    @Column(nullable = true)
    private String date;

    @Column(name = "date_start", nullable = true)
    private String dateStart;

    @Column(name = "date_end", nullable = true)
    private String dateEnd;

    @Column(name = "is_close")
    private boolean isClose = false;

    @Column(name = "is_logout")
    private boolean isLogout = false;

    // Many-to-One relationship with Percepteur
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "percepteur_id", nullable = false)
    private Percepteur percepteur;

    // Many-to-One relationship with Voie
    @ManyToOne
    @JoinColumn(name = "voie_id", nullable = true)
    private Voie voie;

    // Many-to-One relationship with TypeVacation
    @ManyToOne
    @JoinColumn(name = "type_vacation_id", nullable = true)
    private TypeVacation typeVacation;

    // Enum for the vacation status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VacationStatus statut = VacationStatus.pending;


}
