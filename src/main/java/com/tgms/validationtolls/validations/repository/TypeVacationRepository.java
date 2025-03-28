package com.tgms.validationtolls.validations.repository;

import com.tgms.validationtolls.validations.model.TypeVacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeVacationRepository  extends JpaRepository<TypeVacation, Long> {

    @Query("SELECT v FROM TypeVacation v ORDER BY v.startTime ASC")
    List<TypeVacation> findAllSortedByStartTime();

}
