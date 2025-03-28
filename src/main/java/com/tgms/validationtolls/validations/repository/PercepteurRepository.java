package com.tgms.validationtolls.validations.repository;

import com.tgms.validationtolls.validations.model.Percepteur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PercepteurRepository  extends JpaRepository<Percepteur, Long> {


   // List<PercepteurProjection> findAllBy(Sort sort);
    @Query("SELECT p.id AS id, p.nom AS nom, p.prenom AS prenom, p.codePercepteur AS codePercepteur FROM Percepteur p")
    List<PercepteurProjection> findAllProjectedBy(Sort sort);




}
