package com.tgms.validationtolls.validations.repository;

import com.tgms.validationtolls.validations.model.Sites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Sites, Long> {
    // JpaRepository already provides basic CRUD methods like save(), findAll(), findById(), etc.
}
