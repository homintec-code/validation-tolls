package com.tgms.validationtolls.gate.repository;

import com.tgms.validationtolls.gate.model.GateCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface GateCountRepository extends JpaRepository<GateCount, Long> {
    long countByPercepteurIdAndDateBetweenAndInvalidTrue(Long percepteurId, Date dateDebut, Date dateFin);

    long countByPercepteurIdAndVacationIdAndInvalidTrue(Long percepteurId, Long vacationId);

    long countByPercepteurIdAndDateBetweenAndInsufTrue(Long percepteurId, Date dateDebut, Date dateFin);

    long countByPercepteurIdAndVacationIdAndInsufTrue(Long percepteurId, Long vacationId);
    // Custom query methods can be added here
}