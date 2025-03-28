package com.tgms.validationtolls.validations.controllers;
import com.tgms.validationtolls.validations.dto.*;
import com.tgms.validationtolls.validations.model.Validation;
import com.tgms.validationtolls.validations.service.StatistiqueService;
import com.tgms.validationtolls.validations.service.ValidationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/validation")
public class ValidationController {

    private final ValidationsService validationService;


    private final StatistiqueService statistiqueService;

    @Autowired
    public ValidationController(StatistiqueService statistiqueService, ValidationsService validationService ) {
        this.statistiqueService = statistiqueService;
        this.validationService = validationService;

    }

    @PostMapping
    @Async
    public ResponseEntity<Validation> createValidation(@RequestBody ValidationDto validation) throws IOException {
        Validation response  =  validationService.create(validation);
        return ResponseEntity.ok(response);

    }



    // Other controller methods

    @GetMapping
    public List<Validation> findAll() {
        return validationService.findAll();
    }

    @GetMapping("/{id}")
    public Validation findOne(@PathVariable Long id) {
        return validationService.findOne(id);
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable Long id) {
        validationService.remove(id);
        return;
    }

    @PostMapping("/get/validation/byPercepteur")
    public List<Validation> getValidationByPercepteur(@RequestBody ValidationPercepteurDto validationPercepteurDto) {
        return (List<Validation>) validationService.getValidationByPercepteur(validationPercepteurDto.getPercepteurId());
    }

    @PostMapping("/get/validation/bycabine")
    public List<Validation> getValidationByCabine(@RequestBody ValidationCabineDto validationCabineDto) {
        return (List<Validation>) validationService.getValidationByCabine(validationCabineDto.getCabineId());
    }

    @PostMapping("/get/validation/byVacation")
    public List<Validation> getValidationByVacation(@RequestBody ValidationVacationDto validationVacationDto) {
        return validationService.getValidationByVacation(validationVacationDto.getVacationId());
    }

    @PostMapping("/info/byVacation")
    public Map<String, Object> getInforValidationByVacation(@RequestBody ValidationVacationDto validationVacationDto) {
        return (Map<String, Object>) validationService.getInforValidationByVacation(validationVacationDto.getVacationId());
    }

    @GetMapping("/get/statistic/Trafic")
    public List<Map<String, Object>> getStatisticTraficByWay() {

        return statistiqueService.getSumStatisticTraficByWay();
    }

    @GetMapping("/sum/statistic/recettes/day")//ok
    public List<Map<String, Object>> getSumStatisticTraficByWay() {

        return statistiqueService.getSumStatisticTraficByWay();
    }

    @GetMapping("/get/statistic/Trafic/month")
    public List<Map<String, Object>> getStatisticMonthTraficByWay() {
        return statistiqueService.getStatisticMonthTraficByWay();
    }

    @GetMapping("/get/statistic/sum/recettes") //ok
    public List<Map<String, Object>> getStatisticSumByByMonth() {
        return statistiqueService.getStatisticSumByByMonth();
    }

    @GetMapping("/get/statistic/infos")
    public Map<String, Object> getTotalStatitistiques() {
        return statistiqueService.getTotalStatitistiques();
    }


    @PostMapping("/get/statistique/vacationId")
    public ResponseEntity<?> getStatistqueDataValidationByVacationId(@RequestBody InfractionDto infractionDto) {
        Integer vacationId = Integer.parseInt(infractionDto.getPercepteurId());
        Object result = validationService.getStatistqueDataValidationByVacation(Long.valueOf(vacationId));
        return ResponseEntity.ok(result);
    }


    @GetMapping("/export/coupon/endVacation/{vacationId}")
    public ResponseEntity<StatistiqueValidationDto> exportCouponEndVacation(@PathVariable Long vacationId) {
        StatistiqueValidationDto htmlResource = validationService.exportCouponEndVacationHtml(vacationId);
        return ResponseEntity.ok(htmlResource);
    }

    @GetMapping("/test/coupon/endVacation/{vacationId}")
    public ResponseEntity<Validation> exportCouponEndVacationd(@PathVariable Long vacationId) {
        Validation validation = validationService.getStatistqueDataValidationByPercepteur(vacationId);
        return ResponseEntity.ok(validation);
    }



}
