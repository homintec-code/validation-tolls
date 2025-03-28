package com.tgms.validationtolls.validations.repository;

import com.tgms.validationtolls.validations.model.Voie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VoieRepository  extends JpaRepository<Voie, Long> {
    @Query("SELECT v FROM Voie v WHERE v.id NOT IN :ids")
    List<Voie> findVoiesNotInIds(@Param("ids") List<Long> ids);

    @Query("SELECT v FROM Voie v " +
            "JOIN FETCH v.site s " +
            "WHERE s.id=:siteId AND v.type=:type ORDER BY v.nom")
    List<Voie> findVoiesByTypeAndSite(@Param("type") String type, @Param("siteId") Long siteId);


    @Query("SELECT v FROM Voie v WHERE v.site.id=:siteId ORDER BY v.nom")
    List<Voie> findBySiteId(Long siteId);
}
