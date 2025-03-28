package com.tgms.validationtolls.validations.controllers;

import com.tgms.validationtolls.validations.model.Vacation;
import com.tgms.validationtolls.validations.service.TypeVacationService;
import com.tgms.validationtolls.validations.service.VacationService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/vacations")
public class VacationController {

    @Autowired
    private VacationService vacationService;

    @Autowired
    private TypeVacationService typeVacationService;

    // POST: Create a new vacation
    @PostMapping
    public ResponseEntity<Vacation> createVacation(@RequestBody Vacation vacation) {
        Vacation createdVacation = vacationService.createVacation(vacation);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdVacation);
    }

    // GET: Find all vacations
    @GetMapping
    public ResponseEntity<List<Vacation>> findAllVacations() {
        List<Vacation> vacations = vacationService.findAllVacations();
        return ResponseEntity.ok(vacations);
    }




}
