package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.WebSocket.WebSocketService;
import com.tgms.validationtolls.gate.service.GateCountService;
import com.tgms.validationtolls.utils.FieldParserUtils;
import com.tgms.validationtolls.utils.PaginationUtils;
import com.tgms.validationtolls.validations.dto.*;
import com.tgms.validationtolls.validations.model.*;
import com.tgms.validationtolls.validations.repository.ValidationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Service
@Transactional
public class ValidationsService {

    private final ValidationsRepository validationRepository;

    private final VacationService vacationService;

    private final VoieService voieService;

    private final PercepteurService percepteurService;

    private final GateCountService gateCountService;
    private final KafkaTemplate<String, Validation> kafkaTemplate;

    @Autowired
    public ValidationsService(ValidationsRepository validationRepository, VacationService vacationService, VoieService voieService, PercepteurService percepteurService, WebSocketService webSocketService, GateCountService gateCountService, LogsService logsService, KafkaTemplate<String, Validation> kafkaTemplate, KafkaTemplate<String, Validation> kafkaTemplate1) {
        this.validationRepository = validationRepository;
        this.vacationService = vacationService;
        this.voieService = voieService;
        this.percepteurService = percepteurService;
        this.gateCountService = gateCountService;
        this.kafkaTemplate = kafkaTemplate1;
    }




    public Validation create(ValidationDto validationDto) throws IOException {
        // Check for existing validation

        // Parse IDs and fields
        int prix = FieldParserUtils.parseNumericField(validationDto.getPrix(), 0);
        int es = FieldParserUtils.parseNumericField(validationDto.getEs(), 2);
        int essieuCapter = FieldParserUtils.parseNumericField(validationDto.getEssieuCapter(), 2);
        int essieuCorriger = FieldParserUtils.parseNumericField(validationDto.getEssieuCorriger(), essieuCapter);

        // Fetch related entities in parallel
        LocalTime time = LocalTime.now();
        LocalDate localDate = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.now();
        // Create and assign values to the validation entity
        Validation validation = new Validation();
        validation.setRefer(UUID.randomUUID().toString());
        validation.setDate(localDate);
        validation.setHeure(String.valueOf(time));
        validation.setPrix((double) prix);
        validation.setCaisse(Integer.parseInt(validationDto.getCaisse()));
        validation.setEs(es);
        validation.setType(validationDto.getType());
        validation.setPlaque(validationDto.getPlaque());
        validation.setPlaque(validationDto.getPlaque());
        validation.setTicketNumber(validationDto.getTicketNumber());
        validation.setSociete(validationDto.getSociete());
        validation.setEssieuCapter(essieuCapter);
        validation.setEssieuCorriger(essieuCorriger);
        validation.setCmaes(FieldParserUtils.parseStringField(validationDto.getCmaes(), "0"));
        validation.setPtrac(FieldParserUtils.parseStringField(validationDto.getPtrac(), "0"));
        validation.setOver(FieldParserUtils.parseStringField(validationDto.getOver(), "0"));
        validation.setVisa(FieldParserUtils.parseStringField(validationDto.getVisa(), "0"));
        validation.setPtt(FieldParserUtils.parseStringField(validationDto.getPtt(), "0"));
        validation.setViolation(FieldParserUtils.parseBooleanField(validationDto.getIsViolation()));
        validation.setGate(FieldParserUtils.parseBooleanField(validationDto.getIsGate()));
        validation.setExo(FieldParserUtils.parseBooleanField(validationDto.getIsExo()));
        validation.setIsLoop(0);
        validation.setDateApi(dateTime);
        validation.setNomenclature(validationDto.getNomenclature());
        validation.setVacationId(validationDto.getVacationId());
        validation.setVoieId(validationDto.getVoieId());
        validation.setPercepteurId(validationDto.getPercepteurId());

        // Save the validation entity
        try {
            Validation savedValidation = validationRepository.save(validation);
            // Envoi immédiat à Kafka
            kafkaTemplate.send("validations-topic", savedValidation.getId().toString(), savedValidation);

            return savedValidation;

        } catch (Exception e) {
            if (e.getMessage().contains("constraint violation")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les données reçues ne sont pas valides.");

            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Les données reçues ne sont pas valides.");
        }

    }





    
    public StatistiqueValidationDto getStatistqueDataValidationByVacation(Long vacationId) {
        try {
            Vacation vacation = vacationService.findOne(vacationId);

            Validation validationNow = this.getStatistqueDataValidationByPercepteur(vacationId);
            if (vacation == null || validationNow == null) {
                return getEmptyStatistics(); // Return default or empty statistics
            }

            Double prix = validationNow.getPrix();
            String ptrac = validationNow.getPtrac();

            Voie voie = voieService.findOne(vacation.getVoie().getId());
            Integer nbrGate = this.getShowValidationByPercepteurTypeGate(vacationId);
            Double montantCaisse = sumValidationByPercepteurTypeisExoFalse(vacationId);
            Integer violation = getShowValidationByPercepteurTypeVacationViolation(vacationId);
            Integer nbreVehicule = getShowValidationByPercepteurTypeVacationNbreVehicule(vacationId, 2);
            Integer nbreGateInvalid = Math.toIntExact(gateCountService.getLastdataCountInvalidVacation(vacationId, vacation.getPercepteur().getId()));
            Integer nbreGateInsuf = Math.toIntExact(gateCountService.getLastdataCountInsufVacation(vacationId, vacation.getPercepteur().getId()));
            Integer nbrePl = getShowValidationByPercepteurTypeVacationByCategorie(vacationId,"PL");

            Integer es2 = getShowValidationByPercepteurTypeVacationGlobal(vacationId,  "PL",2);
            Integer es3 = getShowValidationByPercepteurTypeVacationGlobal(vacationId,  "PL",3);
            Integer es4 = getShowValidationByPercepteurTypeVacationGlobal(vacationId,  "PL",4);
            Integer es5 = getShowValidationByPercepteurTypeVacationGlobal(vacationId,  "PL",5);
            Integer es6 = getShowValidationByPercepteurTypeVacationGlobal(vacationId,  "PL",6);
            Integer es7 = getShowValidationByPercepteurTypeVacationGlobal(vacationId,  "PL",7);
            Integer es8 = getShowValidationByPercepteurTypeVacationGlobal(vacationId,  "PL",8);
            Integer es9 = getShowValidationByPercepteurTypeVacationGlobal(vacationId,  "PL",9);
            Integer vl = getShowValidationByPercepteurTypeVacationByCategorie(vacationId, "VL");
            Integer moto = getShowValidationByPercepteurTypeVacationByCategorie(vacationId, "MOTO");
            Integer tricycle = getShowValidationByPercepteurTypeVacationByCategorie(vacationId, "TRICYCLE");
            Integer minibus = getShowValidationByPercepteurTypeVacationByCategorie(vacationId, "MINIBUS");
            Integer autobus = getShowValidationByPercepteurTypeVacationByCategorie(vacationId, "AUTOBUS");
            Integer pl = getShowValidationByPercepteurTypeVacationByCategorie(vacationId, "PL");
            Integer exo = getShowValidationByPercepteurTypeVacationExo(vacationId);
            Double montantExo = sumValidationByPercepteurTypeExo(vacationId);
            StatistiqueValidationDto dataStatistics = new StatistiqueValidationDto();
            dataStatistics.setVoie(new VoieDto(vacation.getVoie().getNom(), vacation.getVoie().getIp(),vacation.getVoie().getSens(),vacation.getVoie().getType(), vacation.getVoie().getSite().getId()));
            dataStatistics.setSite(vacation.getVoie().getSite().getNom());
            dataStatistics.setDateStart(vacation.getDateStart());
            dataStatistics.setDateEnd(vacation.getDateEnd());
            dataStatistics.setPercepteur(new PercepteurDTO(vacation.getPercepteur().getId(),vacation.getPercepteur().getNom(),vacation.getPercepteur().getPrenom()));
            dataStatistics.setMontantCaisse(montantCaisse);
            dataStatistics.setMontant(prix);
            dataStatistics.setMontantPenalite(Double.valueOf(violation));
            dataStatistics.setNbrePenalite(0);
            dataStatistics.setViolation(violation);
            dataStatistics.setNbreVehicule(nbreVehicule);
            dataStatistics.setNbreGateSucces(nbrGate);
            dataStatistics.setMontantGate(nbrGate * voie.getSite().getTarif());
            dataStatistics.setNbreGateInvalid(nbreGateInvalid);
            dataStatistics.setNbreGateInsuf(nbreGateInsuf);
            dataStatistics.setNbrePl(nbrePl);
            dataStatistics.setPdTotalTransport(0.0);
            dataStatistics.setTypeVehicule(ptrac);
            dataStatistics.setEs2(es2);
            dataStatistics.setEs3(es3);
            dataStatistics.setEs4(es4);
            dataStatistics.setEs5(es5);
            dataStatistics.setEs6(es6);
            dataStatistics.setEs7(es7);
            dataStatistics.setEs8(es8);
            dataStatistics.setEs9(es9);
            dataStatistics.setVl(vl);
            dataStatistics.setMoto(moto);
            dataStatistics.setTricycle(tricycle);
            dataStatistics.setMinibus(minibus);
            dataStatistics.setAutobus(autobus);
            dataStatistics.setPl(pl);
            dataStatistics.setExo(exo);
            dataStatistics.setMontantExo(montantExo);
            System.out.println("dataStatistics: "  + dataStatistics);
            return dataStatistics;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recupération d'un element: " + e.getMessage());
        }
    }

    private Integer getShowValidationByPercepteurTypeGate(Long vacationId) {
        return Math.toIntExact(validationRepository.countByIsGate(vacationId));
    }

    private Integer getShowValidationByPercepteurTypeVacationExo(Long vacationId) {
        return Math.toIntExact(validationRepository.countByIsExo(vacationId));
    }

    private Integer getShowValidationByPercepteurTypeVacationViolation( Long vacationId) {
        return Math.toIntExact(validationRepository.countByViolation(vacationId));
    }

    private Integer getShowValidationByPercepteurTypeVacationNbreVehicule(Long vacationId,Integer es) {

        return Math.toIntExact(validationRepository.countByPassageNbreIsNotExoByVacationByPercepteur(vacationId,es));
    }


    private StatistiqueValidationDto getEmptyStatistics() {
        return new StatistiqueValidationDto();
    }


    private Integer getShowValidationByPercepteurTypeVacationGlobal(Long vacationId, String ptrac, Integer es) {
        // Implement logic to count validations
        return Math.toIntExact(validationRepository.countByConditionsByVacationByPercepteurByEsByPtrcByIsExoByGate(vacationId, es, ptrac));
    }


    private Integer getShowValidationByPercepteurTypeVacationByCategorie(Long vacationId, String ptrac) {
        // Implement logic to count validations
        return Math.toIntExact(validationRepository.countByConditionsByVacationByPercepteurByisGateByByIsExo(vacationId, ptrac));
    }

    private Double sumValidationByPercepteurTypeisExoFalse(Long vacationId) {
        // Implement logic to sum validations
        return validationRepository.sumValidationByPercepteurTypeIsExoFalse(vacationId);
    }

    private Double sumValidationByPercepteurTypeExo(Long vacationId) {
        // Implement logic to sum validations for exo
        return validationRepository.sumValidationByIsExoTrue(vacationId);
    }


    public Validation getValidationByPercepteur(Long percepteurId) {
        Optional<Validation> latestRecord = validationRepository.findLatestByPercepteurId(percepteurId);
        return latestRecord.orElse(null);
    }

    public Validation findOne(Long id) {
        return validationRepository.findById(id).orElse(null);
    }

    public void remove(Long id) {
         validationRepository.deleteById(id);
    }

    public List<Validation> findAll() {
        return  validationRepository.findAll();
    }

    public List<Validation>  getValidationByVacation(Long vacationId) {

        return validationRepository.queryValidationsByVacation(vacationId);
    }

    public Validation getValidationByCabine(Long voieId) {

        return validationRepository.queryValidationsByVoie_Id(voieId);
    }

    public Map getInforValidationByVacation(Long vacationId) {


       return (Map) this.getStatistqueDataValidationByVacation(vacationId);

    }


    public Validation getStatistqueDataValidationByPercepteur(Long vacationId) {


        Validation validations =    validationRepository.findByVacationIdByLimt(vacationId);

        System.out.println("vacationId" + validations);

        return validations;
    }

    public StatistiqueValidationDto exportCouponEndVacationHtml(Long vacationId) {
        return this.getStatistqueDataValidationByVacation(vacationId);
    }

    private List<Object> getRangeWithDots(int currentPage, int totalPages) {
        // Implement your logic to generate page numbers with dots
        return PaginationUtils.getRangeWithDots(currentPage,totalPages); // Example
    }

}