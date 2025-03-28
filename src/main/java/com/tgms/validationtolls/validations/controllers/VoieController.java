package com.tgms.validationtolls.validations.controllers;

import com.tgms.validationtolls.validations.dto.PaginationResponse;
import com.tgms.validationtolls.validations.dto.VoieBySiteDto;
import com.tgms.validationtolls.validations.dto.VoieDto;
import com.tgms.validationtolls.validations.dto.VoieUpdateDto;
import com.tgms.validationtolls.validations.model.Voie;
import com.tgms.validationtolls.validations.service.VoieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/voies")
public class VoieController {

    @Autowired
    private VoieService voieService;

    // Create a new Voie
    @PostMapping
    public ResponseEntity<?> createVoie(@RequestBody VoieDto voie) {
        try {
            Voie createdVoie = voieService.createVoie(voie);
            return ResponseEntity.ok(createdVoie);  // Return created entity with HTTP 200 status
        } catch (Exception e) {
            // Log the error (optional, for debugging purposes)
            e.printStackTrace();

           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(null);
        }
    }

    // Get all Voies
    @GetMapping
    public ResponseEntity<List<Voie>> getAllVoies() {
        List<Voie> voies = voieService.getVoies();
        return ResponseEntity.ok(voies);
    }

    @GetMapping("/pagination")
    public PaginationResponse<Voie> findAllPagination(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit) {

        Pageable pageable = (Pageable) PageRequest.of(page - 1, limit);
        Page<Voie> result = voieService.findAllPagination(pageable);
        return new PaginationResponse<>(
                result.getContent(), // Les données
                result.getTotalElements(), // Le nombre total d'éléments
                result.getTotalPages(), // Le nombre total de pages
                page, // La page actuelle
                limit // La limite par page
        );
    }
    // Update a Voie entity by ID
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateVoie(@PathVariable Long id, @RequestBody VoieUpdateDto voieDto) {
        voieService.updateVoie(id, voieDto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "percepteur update");
        return ResponseEntity.ok(response);
    }

    // Delete a Voie entity by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVoie(@PathVariable Long id) {
        voieService.deleteVoie(id);
        return ResponseEntity.ok("Voie deleted");
    }

    // Find Voie entities by site_id
    @PostMapping("/bySite")
    public ResponseEntity<List<Voie>> getVoieBySite(@RequestBody VoieBySiteDto voieBySiteDto) {
        Long siteId = Long.parseLong(voieBySiteDto.getSiteId());
        List<Voie> voies = voieService.findVoiesBySiteId(siteId);
        return ResponseEntity.ok(voies);
    }

    // Find Voie entities by site_id and transform the response for LabVIEW
    @PostMapping("/bySite/forLabview")
    public ResponseEntity<List<Map<String, String>>> getVoieBySiteForLabview(@RequestBody VoieBySiteDto voieBySiteDto) {
        Long siteId = Long.parseLong(voieBySiteDto.getSiteId());
        List<Map<String, String>> voies = voieService.findVoiesBySiteIdForLabview(siteId);
        return ResponseEntity.ok(voies);
    }

    // Find Voie entities by site_id and transform the response for LabVIEW (gate)
    @PostMapping("/bySite/forLabview/gate")
    public ResponseEntity<List<Map<String, String>>> findOneBySiteForLabviewGate(@RequestBody VoieBySiteDto voieBySiteDto) {
        Long siteId = Long.parseLong(voieBySiteDto.getSiteId());
        List<Map<String, String>> voies = voieService.findVoiesBySiteIdForLabview(siteId);
        return ResponseEntity.ok(voies);
    }
}
