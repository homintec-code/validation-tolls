package com.tgms.validationtolls.gate.service;

import com.tgms.validationtolls.gate.dto.GateCountDto;
import com.tgms.validationtolls.gate.model.GateCount;
import com.tgms.validationtolls.gate.repository.GateCountRepository;
import com.tgms.validationtolls.validations.model.Percepteur;
import com.tgms.validationtolls.validations.model.Vacation;
import com.tgms.validationtolls.validations.service.PercepteurService;
import com.tgms.validationtolls.validations.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class GateCountService {

    @Autowired
    private GateCountRepository gateCountRepository;

    @Autowired
    private PercepteurService percepteurService;

    @Autowired
    private VacationService vacationService;

    @Transactional
    public GateCount saveGateCount(GateCountDto gateCountDto) {
        Percepteur percepteur = percepteurService.findOne(Long.parseLong(gateCountDto.getPercepteur_id()));
        Vacation vacation = vacationService.findOne(Long.parseLong(gateCountDto.getVacation_id()));

        GateCount gateCount = new GateCount();
        gateCount.setDate(LocalDateTime.now());
        gateCount.setInsuf(Integer.parseInt(gateCountDto.getInsuf()) != 0);
        gateCount.setInvalid(Integer.parseInt(gateCountDto.getInvalid()) != 0);
        gateCount.setPercepteur(percepteur);
        gateCount.setVacation(vacation);

        return gateCountRepository.save(gateCount);
    }

    public long getLastdataCountInvalid(Date dateDebut, Date dateFin, Long percepteurId) {
        return gateCountRepository.countByPercepteurIdAndDateBetweenAndInvalidTrue(percepteurId, dateDebut, dateFin);
    }

    public long getLastdataCountInvalidVacation(Long vacationId, Long percepteurId) {
        return gateCountRepository.countByPercepteurIdAndVacationIdAndInvalidTrue(percepteurId, vacationId);
    }

    public long getLastdataCountInsuf(Date dateDebut, Date dateFin, Long percepteurId) {
        return gateCountRepository.countByPercepteurIdAndDateBetweenAndInsufTrue(percepteurId, dateDebut, dateFin);
    }

    public long getLastdataCountInsufVacation(Long vacationId, Long percepteurId) {
        return gateCountRepository.countByPercepteurIdAndVacationIdAndInsufTrue(percepteurId, vacationId);
    }
}