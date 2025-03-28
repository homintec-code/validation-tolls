package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.validations.model.Voie;
import com.tgms.validationtolls.validations.repository.ValidationsRepository;
import com.tgms.validationtolls.validations.repository.VoieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatistiqueService {

    private final ValidationsRepository validationRepository;

    private final VoieRepository voieRepository;


    @Autowired
    public StatistiqueService(ValidationsRepository validationRepository,VoieRepository voieRepository) {
        this.validationRepository = validationRepository;
        this.voieRepository = voieRepository;

    }

    public List<Map<String, Object>> getSumStatisticTraficByWay() {
        List<Voie> voies = voieRepository.findAll();
        LocalDate today = LocalDate.now();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Voie voie : voies) {
            Double sumTrafic = validationRepository.sumByVoieIdAndDate(voie.getId(), today);
            Map<String, Object> entry = new HashMap<>();
            entry.put("voie", voie.getNom());
            entry.put("countTrafic", sumTrafic != null ? sumTrafic : 0.0);
            result.add(entry);
        }

        return result;
    }

    public List<Map<String, Object>> getStatisticMonthTraficByWay() {
        List<Voie> voies = voieRepository.findAll();
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Voie voie : voies) {
            Long countTrafic = validationRepository.countByVoieIdAndMonthAndYear(voie.getId(), month, year);
            Map<String, Object> entry = new HashMap<>();
            entry.put("voie", voie.getNom());
            entry.put("countTrafic", countTrafic);
            result.add(entry);
        }

        return result;
    }

    public List<Map<String, Object>> getStatisticSumByByMonth() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        List<Map<String, Object>> result = new ArrayList<>();

        // Générer les 12 derniers mois
        YearMonth startMonth = YearMonth.now().minusMonths(11);
        YearMonth endMonth = YearMonth.now();

        for (YearMonth month = startMonth; !month.isAfter(endMonth); month = month.plusMonths(1)) {
            Double sumTrafic = validationRepository.sumByMonthAndYear(month.getMonthValue(), year);
            Map<String, Object> entry = new HashMap<>();
            entry.put("month", month.getMonth().toString());
            entry.put("recettes", sumTrafic != null ? sumTrafic : 0.0);
            result.add(entry);
        }

        return result;
    }

    public Map<String, Object> getTotalStatitistiques() {
        LocalDate now = LocalDate.now();
        int month = now.getMonthValue();
        int year = now.getYear();

        Double recettesTotals = validationRepository.sumByMonthAndYear(month, year);
        Long tarifcMensuel = validationRepository.countByMonthAndYear(month, year);

        Double recettesMensuel = validationRepository.sumByMonth(month);

        Long tarifcTotales = validationRepository.count();

        Map<String, Object> totals = new HashMap<>();
        totals.put("recettesTotals", recettesTotals != null ? recettesTotals : 0.0);
        totals.put("tarifcMensuel", tarifcMensuel);
        totals.put("recettesMensuel", recettesMensuel);
        totals.put("tarifcTotales", tarifcTotales);


        return totals;
    }


    public Map<String, Object> getStatisticTraficByWay() {
        List<Voie> voies = voieRepository.findAll();
        LocalDate today = LocalDate.now();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Voie voie : voies) {
            Double sumTrafic = validationRepository.sumByVoieIdAndDate(voie.getId(), today);
            Map<String, Object> entry = new HashMap<>();
            entry.put("voie", voie.getNom());
            entry.put("countTrafic", sumTrafic != null ? sumTrafic : 0.0);
            result.add(entry);
        }

        return null;
    }
}
