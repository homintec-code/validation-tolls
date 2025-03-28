package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.validations.dto.VoieDto;
import com.tgms.validationtolls.validations.dto.VoieUpdateDto;
import com.tgms.validationtolls.validations.model.Sites;
import com.tgms.validationtolls.validations.model.Voie;
import com.tgms.validationtolls.validations.repository.VoieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class VoieService {


    private final VoieRepository voieRepository;
    private final SiteService siteService;

    @Autowired
    public VoieService(VoieRepository voieRepository, SiteService siteService) {
        this.voieRepository = voieRepository;
        this.siteService = siteService;
    }


    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Voie createVoie(VoieDto voieDto) {
        try {
            // Retrieve the associated site by ID
            Sites site = siteService.getSiteById(voieDto.site_id());

            // Create a new Voie entity and set its fields from VoieDto
            Voie newVoie = new Voie();
            newVoie.setNom(voieDto.nom());  // Set values from VoieDto
            newVoie.setIp(voieDto.ip());
            newVoie.setSens(voieDto.sens());
            newVoie.setType(voieDto.type());
            newVoie.setSite(site);
            // Update entity
          return  voieRepository.save(newVoie);

        } catch (ObjectOptimisticLockingFailureException e) {
            // Handle exception and show error message
            System.out.println("Optimistic Locking Failure: " + e.getMessage());

            return null;
        }


    }

    public List<Voie> getVoies() {
        return new ArrayList<>(voieRepository.findAll());
    }

    public Page<Voie> findAllPagination(Pageable pageable) {

        Page<Voie> result = voieRepository.findAll(pageable);
        return  result;
    }

    public Voie findOne(Long voieId) {

        return  voieRepository.findById(voieId).orElse(null);
    }

    public List<Voie> findOneByType(String type, Long siteId) {
        return voieRepository.findVoiesByTypeAndSite(type,siteId);
    }

    public List<Voie> findOneBySite(Long siteId) {
        return voieRepository.findBySiteId(siteId);
    }

    // Update a Voie entity
    public Object updateVoie(Long id, VoieUpdateDto voieDto) {
        // Update machine status via Gatewayo
        Voie voie = voieRepository.findById(id).orElse(null);
        assert voie != null;
        voie.setNom(voieDto.getNom());
        voie.setIp(voieDto.getIp());
        voie.setSens(voieDto.getSens());
        voie.setSite(siteService.getSiteById(voieDto.getSite_id()));
       return voieRepository.save(voie);
    }

    // Delete a Voie entity
    public void deleteVoie(Long id) {
        voieRepository.deleteById(id);
    }

    // Find Voie entities by site_id
    public List<Voie> findVoiesBySiteId(Long siteId) {
        return voieRepository.findBySiteId(siteId);
    }

    // Find Voie entities by site_id and transform the response for LabVIEW
    public List<Map<String, String>> findVoiesBySiteIdForLabview(Long siteId) {
        return voieRepository.findBySiteId(siteId).stream()
                .map(voie -> Map.of(
                        "id", String.valueOf(voie.getId()),
                        "nom", voie.getNom(),
                        "sens", voie.getSens()
                ))
                .collect(Collectors.toList());
    }

    public List<Voie> findAll() {
        return  voieRepository.findAll();
    }
}

