package com.tgms.validationtolls.validations.controllers;

import com.tgms.validationtolls.validations.dto.*;
import com.tgms.validationtolls.validations.model.Logs;
import com.tgms.validationtolls.validations.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
    @RequestMapping("/api/v1/logs")
public class LogsController {

    @Autowired
    private LogsService logsService;

    // GET /logs/all
    @GetMapping("/all")
    public List<Logs> findAll() {
        return logsService.findAll();
    }

    // GET /logs/{id}
    @GetMapping("/{id}")
    public Logs findOne(@PathVariable Long id) {
        return logsService.findOne(id);
    }

    // GET /logs/voie/{voie_id}
    @GetMapping("/voie/{voie_id}")
    public Logs findLoggerByVoie(@PathVariable Long voieId) {
        return logsService.findLogsByPercepteurIslogger(voieId);
    }

    // POST /logs/voie/forLabView
    @PostMapping("/voie/forLabView")
    public Object getPercepteurLoggerByVoieForLabview(@RequestBody LogVoieIdDto logVoieIdDto) {
        return logsService.findLogsByPercepteurIsloggerForLabView(logVoieIdDto.getVoieId());
    }




    // POST /logs/logout
    @PostMapping("/logout")
    public Object logoutPercepteur(@RequestBody LogoutPercepteurDto logoutPercepteurDto) {
        return logsService.logoutPercepteur(logoutPercepteurDto);
    }



    @PostMapping("/type/with/login/percepteur")
    public Object findAllVoiebyTypeWithPercepteurLogin(@RequestBody VoieByTypeDto voie) {
        return logsService.getVoieAndPercepteurLogin(voie.getType(), voie.getSiteId());
    }


    @GetMapping("/with/logger/{voie_id}")
    public Object findByVoieWithLoggerPercepteur(@PathVariable Long voieId) {
        return logsService.cabineWithLoginPercepteur(voieId);
    }

    @GetMapping("/check/disconnection/percepteur/{user_id}")
    public Object findLogByPercepteurDeconnectedCabin(@PathVariable Long userId) {
        return logsService.findLogByPercepteurDeconnectedCabin(userId);
    }



}