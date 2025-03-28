package com.tgms.validationtolls.validations.repository;

import com.tgms.validationtolls.validations.model.Validation;
import com.tgms.validationtolls.validations.model.Voie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ValidationsRepository extends JpaRepository<Validation, Long> {
    boolean existsByRefer(String refer);
    // Custom query methods if necessary

    @Query("SELECT COUNT(v) FROM Validation v WHERE " +
            "v.vacation.id = :vacationId AND " +
            "v.es = :es AND " +
            "v.ptrac = :ptrac AND " +
            "v.isGate = false AND " +
            "v.isExo = false ")
    Long countByConditionsByVacationByPercepteurByEsByPtrcByIsExoByGate(
            @Param("vacationId") Long vacationId,
            @Param("es") Integer es,
            @Param("ptrac") String ptrac
    );


    @Query("SELECT COUNT(v) FROM Validation v WHERE " +
            "v.vacation.id = :vacationId AND " +
            "v.ptrac = :ptrac AND " +
            "v.isGate = false AND " +
            "v.isExo = false")
    Long countByConditionsByVacationByPercepteurByisGateByByIsExo(
            @Param("vacationId") Long vacationId,
            @Param("ptrac") String ptrac
    );


    @Query("SELECT COUNT(v) FROM Validation v WHERE " +
            "v.vacation.id = :vacationId AND " +
            "v.es = :es AND " +
            "v.isGate = false AND " +
            "v.isExo = false")
    Long countByPassageNbreIsNotExoByVacationByPercepteur(
            @Param("vacationId") Long vacationId,
            @Param("es") Integer es
            );



    @Query("SELECT COUNT(v) FROM Validation v WHERE " +
            "v.vacation.id = :vacationId AND " +
            "v.isViolation = true")
    Long countByViolation(
            @Param("vacationId") Long vacationId);


    @Query("SELECT COUNT(v) FROM Validation v WHERE " +
            "v.vacation.id = :vacationId AND " +
            "v.isExo = true")
    Long countByIsExo(
            @Param("vacationId") Long vacationId);



    @Query("SELECT COUNT(v) FROM Validation v WHERE " +
            "v.vacation.id = :vacationId AND " +
            "v.isGate = true")
    Long countByIsGate(
            @Param("vacationId") Long vacationId);


    @Query("SELECT COALESCE(SUM(v.prix), 0) FROM Validation v " +
            "WHERE v.vacation.id = :vacationId " +
            "AND v.isGate = false " +
            "AND v.isExo = false")
    Double sumValidationByPercepteurTypeIsExoFalse(
            @Param("vacationId") Long vacationId);

    @Query("SELECT COALESCE(SUM(v.prix), 0) FROM Validation v " +
            "WHERE v.vacation.id = :vacationId " +
            "AND v.isExo = true")
    Double sumValidationByIsExoTrue(
            @Param("vacationId") Long vacationId);

    Optional<Validation> findLatestByPercepteurId(Long percepteurId);


    @Query("SELECT v FROM Validation v WHERE v.vacation.id = :vacationId")
    List<Validation> queryValidationsByVacation(@Param("vacationId") Long vacationId);

    Validation queryValidationsByVoie_Id(Long voieId);

    Long voie(Voie voie);

    @Query("SELECT COUNT(v) FROM Validation v WHERE v.voie.id = :voieId AND FUNCTION('DATE', v.date) = :today AND v.isExo = false")
    Long countByVoieIdAndDate(@Param("voieId") Long voieId, @Param("today") LocalDate today);

    @Query(value = "SELECT SUM(prix) FROM validation " +
            "WHERE EXTRACT(MONTH FROM date) = EXTRACT(MONTH FROM CAST(:today AS DATE)) " +
            "AND voie_id = :voieId AND is_exo = FALSE", nativeQuery = true)
    Double sumByVoieIdAndDate(@Param("voieId") Long voieId, @Param("today") LocalDate today);


    @Query(value = "SELECT COUNT(*) FROM validation " +
            "WHERE voie_id = :voieId " +
            "AND EXTRACT(MONTH FROM date) = :month " +
            "AND EXTRACT(YEAR FROM date) = :year " +
            "AND is_exo = FALSE", nativeQuery = true)
    Long countByVoieIdAndMonthAndYear(@Param("voieId") Long voieId, @Param("month") int month, @Param("year") int year);

    @Query(value = "SELECT SUM(prix) FROM validation " +
            "WHERE EXTRACT(MONTH FROM created_at) = :month AND EXTRACT(YEAR FROM created_at) = :year", nativeQuery = true)
    Double sumByMonthAndYear(@Param("month") int month, @Param("year") int year);



    @Query(value = "SELECT SUM(prix) FROM validation " +
            "WHERE EXTRACT(MONTH FROM created_at) = :month AND is_exo = FALSE", nativeQuery = true)
    Double sumByMonth(@Param("month") int month);


    @Query(value = "SELECT COUNT(*) FROM validation " +
            "WHERE EXTRACT(MONTH FROM date) = :month " +
            "AND EXTRACT(YEAR FROM date) = :year " +
            "AND is_exo = FALSE", nativeQuery = true)
    Long countByMonthAndYear(@Param("month") int month, @Param("year") int year);


    List<Validation> findByVoieIdAndDateApiBetween(Long voieId, LocalDateTime dateStart, LocalDateTime dateEnd);


    @Query("SELECT v FROM Validation v " +
            "WHERE v.percepteur.id = :percepteurId " +
            "AND v.dateApi >= :dateStart " +
            "AND v.dateApi <= :dateEnd " +
            "ORDER BY v.createdAt DESC")
    List<Validation> findByPercepteurAndDateRange(
            @Param("percepteurId") Long percepteurId,
            @Param("dateStart") LocalDateTime dateStart,
            @Param("dateEnd") LocalDateTime dateEnd
    );

    @Query("SELECT v FROM Validation v " +
            "LEFT JOIN FETCH v.voie " +
            "LEFT JOIN FETCH v.percepteur " +
            "LEFT JOIN FETCH v.vacation " +
            "WHERE v.vacation.id = :vacationId " +
            "ORDER BY v.createdAt DESC " +
            "LIMIT 1") // Ensure only one result is returned
    Optional<Validation> findLatestByVacationId(@Param("vacationId") Long vacationId);



    @Query("SELECT v FROM Validation v " +
            "LEFT JOIN FETCH v.voie " +
            "LEFT JOIN FETCH v.percepteur " +
            "LEFT JOIN FETCH v.vacation " +
            "WHERE v.vacation.id = :vacationId " +
            "ORDER BY v.createdAt DESC " +
            "LIMIT 1") // Ensure only one result is returned
    Validation findByVacationIdByLimt(@Param("vacationId") Long vacationId);



    @Query("SELECT v FROM Validation v " +
            "LEFT JOIN FETCH v.voie " +
            "LEFT JOIN FETCH v.percepteur " +
            "LEFT JOIN FETCH v.vacation " +
            "WHERE v.vacation.id = :vacation_id " +
            "AND v.date >= :dateStart " +
            "AND v.date <= :dateEnd " +  // Fixed: Changed `>=` to `<=` for dateEnd
            "AND v.isExo = :isExo ")  // Ensure this matches the field name in Java
    List<Validation> findValidationsByPercepteurAndDateRange(
            @Param("vacation_id") Long vacation_id,
            @Param("dateStart") LocalDate dateStart,  // Use LocalDate instead of String
            @Param("dateEnd") LocalDate dateEnd,
            @Param("isExo") boolean isExo,
            Pageable pageable);

    @Query("SELECT COUNT(v) FROM Validation v " +
            "WHERE v.vacation.id = :vacation_id " +
            "AND v.date >= :dateStart " +
            "AND v.date <= :dateEnd " +
            "AND v.isExo = :isExo")
    long countValidationsByPercepteurAndDateRange(
            @Param("vacation_id") Long vacation_id,
            @Param("dateStart") LocalDate dateStart,
            @Param("dateEnd") LocalDate dateEnd,
            @Param("isExo") boolean isExo);





    @Query("SELECT v FROM Validation v " +
            "LEFT JOIN FETCH v.voie " +
            "LEFT JOIN FETCH v.percepteur " +
            "LEFT JOIN FETCH v.vacation " +
            "WHERE v.vacation.id = :vacation_id " +
            "AND v.isExo = :isExo ")  // Ensure this matches the field name in Java
    List<Validation> findValidationsByVacation(
            @Param("vacation_id") Long vacation_id,
            @Param("isExo") boolean isExo,
            Pageable pageable);


    @Query("SELECT COUNT(v) FROM Validation v " +
            "WHERE v.vacation.id = :vacation_id " +
            "AND v.isExo = :isExo")
    long countValidationsByVacation(
            @Param("vacation_id") Long vacation_id,
            @Param("isExo") boolean isExo);


    Iterable<Validation> findBySyncedFalse();

    List<Validation> findBySyncedFalseAndLastSyncAttemptBefore(LocalDateTime localDateTime);
}
