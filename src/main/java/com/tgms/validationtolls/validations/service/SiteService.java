package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.validations.model.Sites;
import com.tgms.validationtolls.validations.repository.SiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SiteService {

    private final SiteRepository siteRepository;

    @Autowired
    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    public Sites createSite(Sites site) {
        return siteRepository.save(site);  // Save the Site entity to the database
    }

    public List<Sites> getAllSites() {
        return siteRepository.findAll();  // Get all Sites from the database
    }

    public Sites getSiteById(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    public Sites findOne(Long id) {
        return siteRepository.findById(id).orElse(null);
    }

    public Sites update(Long id, Sites siteDto) {

        Sites site = siteRepository.findById(id).orElse(null);
        if (site != null) {

            site.setNom(siteDto.getNom());
            site.setTarif(site.getTarif());
            return siteRepository.save(site);

        }
        return null;
    }

    public void remove(Long id) {
        siteRepository.deleteById(id);
    }

    public List<String> getAllSitesForLabview() {
        try {
            List<Sites> sites = siteRepository.findAll();

            // Convert each site to a formatted string
            return sites.stream()
                    .map(site -> "id: " + site.getId() + ",nom: " + site.getNom())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Handle exceptions (you can log it here)
            System.err.println("Error fetching sites: " + e.getMessage());
            return List.of("Error fetching sites");
        }
    }



    public List<Sites> findAll() {
        return siteRepository.findAll();
    }

    public Page<Sites> findAllPagination(Pageable pageable) {
        return siteRepository.findAll(pageable);

    }
}