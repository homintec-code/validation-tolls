package com.tgms.validationtolls.validations.repository;

import com.tgms.validationtolls.validations.model.Vacation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VacationRepository  extends JpaRepository<Vacation, Long> {
    List<Vacation> findAllByIsCloseFalseOrderByIdDesc();

    Vacation findFirstByPercepteurIdAndIsCloseFalseOrderByIdDesc(Long percepteurId);



    @Modifying
    @Query("UPDATE Vacation v SET v.statut = :finished WHERE v.id = :planningId")
    void updateStatus(@Param("planningId") Long planningId);


    // Custom query to fetch the Vacation entity with its related entities using JOIN FETCH
    @Query("SELECT v FROM Vacation v " +
            "JOIN FETCH v.percepteur p " +
            "JOIN FETCH v.voie vo " +
            "JOIN FETCH v.typeVacation t " +
            "WHERE v.id = :planningId")
    Vacation findWithRelations(@Param("planningId") Long planningId);

    @Query("SELECT v FROM Vacation v " +
            "JOIN FETCH v.voie voie " +
            "JOIN FETCH v.percepteur percepteur " +
            "JOIN FETCH v.typeVacation typeVacation " +
            "WHERE voie.id = :voieId")
    List<Vacation> findByVoieId(@Param("voieId") Long voieId);



    @Query("SELECT v FROM Vacation v " +
            "JOIN FETCH v.voie voie " +
            "JOIN FETCH v.percepteur percepteur " +
            "JOIN FETCH v.typeVacation typeVacation " +
            "WHERE voie.id = :voieId ORDER BY v.id ")
    Vacation findByVoieIdLimit(@Param("voieId") Long voieId);



    @Query("SELECT v FROM Vacation v " +
            "JOIN FETCH v.voie voie " +
            "JOIN FETCH v.percepteur percepteur " +
            "JOIN FETCH v.typeVacation typeVacation")
    List<Vacation> findAllWithRelations();


    // Use Pageable to enable pagination
    @Query("SELECT v FROM Vacation v " +
            "JOIN FETCH v.voie voie " +
            "JOIN FETCH v.percepteur percepteur " +
            "JOIN FETCH v.typeVacation typeVacation " +
            "ORDER BY v.date DESC")
    Page<Vacation> findAllWithRelationPanignates(Pageable pageable);

    @Query("SELECT v FROM Vacation v " +
            "JOIN v.voie voie " +
            "JOIN v.percepteur percepteur " +
            "JOIN v.typeVacation typeVacation " +
            "WHERE typeVacation.id = :typeVacationId " +
            "AND v.date = :date")
    List<Vacation> findCheckedPlanningByTypeVacationAndDate(@Param("typeVacationId") Long typeVacationId,
                                                            @Param("date") String date);



    // Custom query to find vacations where percepteur is not closing
    @Query("SELECT v FROM Vacation v WHERE v.isClose = false")
    List<Vacation> findByPercepteurIsNotClosing();

    // Custom query to find vacations by percepteur ID
    @Query("SELECT v FROM Vacation v WHERE v.percepteur.id = :percepteurId AND v.isLogout =true ")
    List<VacationProjection> findByPercepteurId(@Param("percepteurId") Long percepteurId);

    Vacation findVacationByIdOrderByIdDesc(Long id);

    Vacation findByVoieIdAndIsLogoutTrue(Long voieId);

    // Custom query to close vacation by percepteur ID
    //@Modifying
    //@Query("UPDATE Vacation v SET v.isClose = true WHERE v.percepteur.id = :percepteurId")
    //void closeVacationByPercepteur(@Param("percepteurId") Long percepteurId);

    @Query("SELECT v FROM Vacation v " +
            "JOIN FETCH v.typeVacation tv " +
            "JOIN FETCH v.percepteur p " +
            "JOIN FETCH v.voie vo " +
            "WHERE vo.id = :voieId " +
            "AND v.date = :date " +
            "AND tv.id = :typeVacationId " +
            "ORDER BY v.id DESC")
    Vacation  findVacationAllByCabineIdWithIsLogoutTypeVactionEnd(
            @Param("voieId") Long voieId,
            @Param("date") String date,
            @Param("typeVacationId") Long typeVacationId);

    @Query("SELECT " +
            "v.id AS id, " +
            "v.dateStart AS dateStart, " +
            "v.dateEnd AS dateEnd, " +
            "v.date AS date, " +
            "tv.label AS typeVacationName, " + // Ensure 'nom' is the correct field name
            "p.nom AS percepteurNom, " +     // Ensure 'nom' is the correct field name
            "p.prenom AS percepteurPrenom, " + // Ensure 'prenom' is the correct field name
            "vo.nom AS voieNom " +           // Ensure 'nom' is the correct field name
            "FROM Vacation v " +
            "JOIN v.typeVacation tv " +      // Jointure avec TypeVacation
            "JOIN v.percepteur p " +         // Jointure avec Percepteur
            "JOIN v.voie vo " +              // Jointure avec Voie
            "WHERE vo.id = :voieId " +
            "AND v.date = :date " +
            "AND tv.id = :typeVacationId " +
            "ORDER BY v.id DESC")
    VacationShowProjection findVacationAllByCabineIdWithIsLogoutTypeVactionEndOL(
            @Param("voieId") Long voieId,
            @Param("date") String date,
            @Param("typeVacationId") Long typeVacationId);




    @Query("SELECT " +
            "v.id AS id, " +
            "v.dateStart AS dateStart, " +
            "v.dateEnd AS dateEnd, " +
            "v.date AS date, " +
            "v.isClose AS isClose,"+
            "tv.label AS typeVacationName, " + // Ensure 'nom' is the correct field name
            "p.nom AS percepteurNom, " +     // Ensure 'nom' is the correct field name
            "p.prenom AS percepteurPrenom, " + // Ensure 'prenom' is the correct field name
            "p.id AS percepteurId " +           // Ensure 'nom' is the correct field name
            "FROM Vacation v " +
            "JOIN v.typeVacation tv " +      // Jointure avec TypeVacation
            "JOIN v.percepteur p " +         // Jointure avec Percepteur
            "JOIN v.voie vo " +              // Jointure avec Voie
            "WHERE vo.id = :voieId " +
            "AND tv.id = :typeVacationId " +
            "ORDER BY v.id DESC LIMIT 1")
    VacationShowProjection findVacationAllByCabineIdWithIsLogoutTypeVactionEndx(
            @Param("voieId") Long voieId,
            @Param("typeVacationId") Long typeVacationId);
}
