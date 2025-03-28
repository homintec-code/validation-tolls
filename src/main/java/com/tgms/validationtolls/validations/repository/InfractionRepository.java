package com.tgms.validationtolls.validations.repository;

import com.tgms.validationtolls.validations.model.Infraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InfractionRepository extends JpaRepository<Infraction, Long> {
    List<Infraction> findByPercepteurIdAndDateDebutBetween(Long percepteurId, Date dateStart, Date dateEnd);

    int countByPercepteurIdAndBoucleExitTrueAndDateDebutBetween(Long percepteurId, LocalDateTime dateStart, LocalDateTime dateEnd);
   // int countByPercepteurIdAndAlimentationBoucleTrueAndDateDebutBetween(Long percepteurId, String dateStart, Date dateEnd);

    int countByPercepteurIdAndAlimentationBoucleTrueAndDateDebutBetween(Long percepteurId, LocalDateTime dateStart, LocalDateTime dateEnd);

    Optional<Infraction> findFirstByPercepteurIdOrderByDateDebutDesc(Long percepteurId);
    // Custom queries can be added here
}