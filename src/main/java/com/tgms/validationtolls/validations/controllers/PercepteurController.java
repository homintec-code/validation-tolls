package com.tgms.validationtolls.validations.controllers;

import com.tgms.validationtolls.validations.dto.*;
import com.tgms.validationtolls.validations.model.Percepteur;
import com.tgms.validationtolls.validations.repository.PercepteurProjection;
import com.tgms.validationtolls.validations.service.PercepteurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/percepteur")
public class PercepteurController {

    private final PercepteurService percepteurService;

    @Autowired
    public PercepteurController(PercepteurService percepteurService) {

        this.percepteurService = percepteurService;
    }

    @PostMapping
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public ResponseEntity<Percepteur> createPercepteur(@RequestBody SavePercepteur percepteur) {
        try {
            Percepteur createdPercepteur = percepteurService.createPercepteur(percepteur);
            return ResponseEntity.ok(createdPercepteur);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);  // Return 500 status on error
        }
    }

    @GetMapping
    public  List<PercepteurProjection> getPercepteursOrderedByNom() {
        return percepteurService.getPercepteursOrderByNom();
    }



    @GetMapping("/{id}")
    public ResponseEntity<Percepteur> findOne(@PathVariable("id") Long id) {
        Percepteur percepteur = percepteurService.findOnes(id);
        if (percepteur == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(percepteur);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> remove(@PathVariable("id") Long id) {
        boolean removed = percepteurService.remove(id);
        if (!removed) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Percepteur with ID " + id + " not found");
        }
        return ResponseEntity.ok("Percepteur successfully deleted");
    }







}

