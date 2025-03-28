package com.tgms.validationtolls.validations.controllers;

import com.tgms.validationtolls.validations.dto.PaginationResponse;
import com.tgms.validationtolls.validations.interfaces.Accessible;
import com.tgms.validationtolls.validations.model.Sites;
import com.tgms.validationtolls.validations.service.SiteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sites")
public class SiteController {

    private final SiteService siteService;

    @Autowired
    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @PostMapping
    public ResponseEntity<Sites> createSite(@RequestBody Sites site) {
        try {
            Sites createdSite = siteService.createSite(site);
            return ResponseEntity.ok(createdSite);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Return 500 status on error
        }
    }

    // GET: Find all sites
    @GetMapping()
    @Accessible  // Assuming Accessible is a custom annotation for access control
    public ResponseEntity<List<Sites>> findAll() {
        List<Sites> sites = siteService.findAll();
        return ResponseEntity.ok(sites);
    }
    @GetMapping("/pagination")
    public PaginationResponse<Sites> findAllPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        Pageable pageable = (Pageable) PageRequest.of(page - 1, limit);
        Page<Sites> result = siteService.findAllPagination(pageable);
        return new PaginationResponse<>(
                result.getContent(), // Les données
                result.getTotalElements(), // Le nombre total d'éléments
                result.getTotalPages(), // Le nombre total de pages
                page, // La page actuelle
                limit // La limite par page
        );
    }

    // GET: Find all sites for Labview
    @GetMapping("/for/labview")
    @Accessible  // Assuming Accessible is a custom annotation for access control
    public ResponseEntity<List<String>> findAllForLabview() {
        List<String> sitesForLabview = siteService.getAllSitesForLabview();
        return ResponseEntity.ok(sitesForLabview);
    }

    // GET: Find one site by id
    @GetMapping("/{id}")
    public ResponseEntity<Sites> findOne(@PathVariable("id") Long id) {
        Sites site = siteService.findOne(id);
        return ResponseEntity.ok(site);
    }

    // PATCH: Update site by id
    @PatchMapping("/{id}")
    @Accessible  // Assuming Accessible is a custom annotation for access control
    public ResponseEntity<Sites> update(@PathVariable("id") Long id, @RequestBody Sites siteDto) {
        Sites updatedSite = siteService.update(id, siteDto);
        return ResponseEntity.ok(updatedSite);
    }

    // DELETE: Remove site by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable("id") Long id) {
        siteService.remove(id);
        return ResponseEntity.noContent().build();  // No content on successful deletion
    }
}
