package com.tgms.validationtolls.validations.repository;

import com.tgms.validationtolls.validations.model.Logs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogsRepository extends JpaRepository<Logs, Long> {

    @Query("SELECT v FROM Logs v WHERE " +
            "v.percepteur.id = :percepteurId AND " +
            "v.voie.id = :voieId AND " +
            "v.isStart = true " +
            "ORDER BY v.id DESC")
    Logs findLogsByPercepteurAndVoieOrderByIdDesc(@Param("percepteurId") Long percepteurId,
                                                  @Param("voieId") Long voieId);

    Logs findByVoieIdAndIsCabinTrue(Long voieId);

    Optional<Logs> findTopByPercepteurIdOrderByIdDesc(Long id);
}
