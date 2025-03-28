package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.validations.dto.InfractionDto;
import com.tgms.validationtolls.validations.model.Infraction;
import com.tgms.validationtolls.validations.model.Logs;
import com.tgms.validationtolls.validations.model.Percepteur;
import com.tgms.validationtolls.validations.repository.InfractionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class InfractionService {

    @Autowired
    private InfractionRepository infractionRepository;

    @Autowired
    private PercepteurService percepteurService;

    @Autowired
    private VoieService voieService;


    @Autowired
    private LogsService logsService;

    @Transactional
    public Infraction save(InfractionDto infractionDto) {
        Percepteur percepteur = percepteurService.findOne((long) Integer.parseInt(infractionDto.getPercepteurId()));

        Infraction infraction = new Infraction();
        infraction.setDateDebut(new Date(infractionDto.getDateDebut()));
        infraction.setDateFin(new Date(infractionDto.getDateFin()));
        infraction.setBoucleExit(infractionDto.getBoucleExit().equals("1"));
        infraction.setAlimentationBoucle(infractionDto.getAlimentationBoucle().equals("1"));
        infraction.setPercepteur(percepteur);

        return infractionRepository.save(infraction);
    }

    public List<Infraction> getInfractionsByPercepteur(Long percepteurId, Date dateStart, Date dateEnd) {
        return infractionRepository.findByPercepteurIdAndDateDebutBetween(percepteurId, dateStart, dateEnd);
    }

    public int getCountInfractionBoucleExit(Long percepteurId, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return infractionRepository.countByPercepteurIdAndBoucleExitTrueAndDateDebutBetween(percepteurId, dateStart, dateEnd);
    }

    public int getCountInfractionAlimentation(Long percepteurId, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return infractionRepository.countByPercepteurIdAndAlimentationBoucleTrueAndDateDebutBetween(percepteurId, dateStart, dateEnd);
    }

    public Optional<Infraction> getLastInfraction(Long percepteurId) {
        return infractionRepository.findFirstByPercepteurIdOrderByDateDebutDesc(percepteurId);
    }

    public Map<String, Object> getDataInfractionByPercepteur(Long voieId) {
        Logs loggerPromise = logsService.findLogsByPercepteurIsLogged(voieId);
        LocalDateTime startDate = LocalDateTime.parse(String.format(String.valueOf(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        LocalDateTime endDate =  LocalDateTime.now();
        Long percepteurId = loggerPromise.getPercepteur().getId();

        int nbreBoucleExit = this.getCountInfractionBoucleExit(percepteurId, startDate, endDate);
        int nbreAlimentation = this.getCountInfractionAlimentation(percepteurId, startDate, endDate);

        Map<String, Object> dataInfractions = new HashMap<>();
        dataInfractions.put("nbre_boucle_exit", nbreBoucleExit);
        dataInfractions.put("nbre_alimentation", nbreAlimentation);
        dataInfractions.put("violation", nbreBoucleExit);
        dataInfractions.put("dataLast", this.getLastInfraction(percepteurId));
        dataInfractions.put("totaleInfraction", nbreAlimentation + nbreBoucleExit);

        return dataInfractions;
    }
}