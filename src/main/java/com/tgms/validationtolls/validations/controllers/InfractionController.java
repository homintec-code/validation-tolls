package com.tgms.validationtolls.validations.controllers;

import com.tgms.validationtolls.validations.dto.InfractionDto;
import com.tgms.validationtolls.validations.model.Infraction;
import com.tgms.validationtolls.validations.service.InfractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/infraction")
public class InfractionController {


    @Autowired
    private InfractionService infractionService;


    @PostMapping
    public ResponseEntity<Infraction> createInfraction(@RequestBody InfractionDto infractionDto) {
        Infraction infraction = infractionService.save(infractionDto);
        return ResponseEntity.ok(infraction);
    }

    @GetMapping("/percepteur/{percepteurId}")
    public ResponseEntity<List<Infraction>> getInfractionsByPercepteur(
            @PathVariable Long percepteurId,
            @RequestParam Date dateStart,
            @RequestParam Date dateEnd) {
        List<Infraction> infractions = infractionService.getInfractionsByPercepteur(percepteurId, dateStart, dateEnd);
        return ResponseEntity.ok(infractions);
    }
}
