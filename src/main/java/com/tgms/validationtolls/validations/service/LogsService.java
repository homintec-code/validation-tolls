package com.tgms.validationtolls.validations.service;


import com.tgms.validationtolls.utils.UtilHelper;
import com.tgms.validationtolls.validations.dto.*;
import com.tgms.validationtolls.validations.enums.LogingStatus;
import com.tgms.validationtolls.validations.enums.VacationStatus;
import com.tgms.validationtolls.validations.model.Logs;
import com.tgms.validationtolls.validations.model.Percepteur;
import com.tgms.validationtolls.validations.model.Vacation;
import com.tgms.validationtolls.validations.model.Voie;
import com.tgms.validationtolls.validations.repository.LogsRepository;
import com.tgms.validationtolls.validations.repository.VacationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class LogsService {

    private final LogsRepository logsRepository;

    private final PercepteurService percepteurService;

    private final VoieService voieService;

    private final VacationService vacationService;

    private final VacationRepository vacationRepository;

    @Autowired
    public LogsService(LogsRepository logsRepository,PercepteurService percepteurService,
                       VoieService voieService,VacationService vacationService,
                       VacationRepository vacationRepository
                       ) {
        this.logsRepository = logsRepository;
        this.percepteurService = percepteurService;
        this.voieService = voieService;
        this.vacationService = vacationService;
        this.vacationRepository = vacationRepository;



    }

    @Transactional
    public Logs create(LogsDto logsDto) {
        Voie voie = voieService.findOne(logsDto.getVoieId());
        Percepteur percepteur = percepteurService.findOne(logsDto.getPercepteurId());
        Logs logs = new Logs();
        logs.setVoie(voie);
        logs.setDate(new Date());
        logs.setHeure(LocalTime.ofSecondOfDay(new Date().getTime()));
        logs.setCabin(true);
        logs.setRefer(generateUUID() + "HOM");
        logs.setCabinAt(getCurrentDateTimeFormatted());
        logs.setStatut(LogingStatus.LOGGER);
        logs.setPercepteur(percepteur);
        logs.setOldPercepteur(percepteur);
        vacationService.updateByPercepteur(logsDto.getVacationIdOld());
        logsRepository.save(logs);
        return logs;
    }

    public String getCurrentDateTimeFormatted() {
        return UtilHelper.getCurrentDateTimeFormatted();
    }

    private String generateUUID() {
        return UtilHelper.generateUUID();
    }

    public Logs findLogsByPercepteurLastLogin(Long percepteurId, Long voieId) {
        return logsRepository.findLogsByPercepteurAndVoieOrderByIdDesc(percepteurId, voieId);
    }

    public List<Logs> findAll() {
        return logsRepository.findAll();
    }

    public Logs findOne(Long id) {
        return logsRepository.findById(id).orElseThrow(() -> new RuntimeException("Logs not found"));
    }

    public Logs findLogsByPercepteurIslogger(Long voieId) {
        return logsRepository.findByVoieIdAndIsCabinTrue(voieId);
    }

    public Map<String, Object> findLogsByPercepteurIsloggerForLabView(Long voieId) {
        try {
            // Récupérer la vacation associée à la voie
            Vacation vacation = vacationService.findVacationAllByCabineIdWithIsLogoutEnd(voieId);

            // Vérifier si la vacation existe
            if (vacation == null) {
                throw new RuntimeException("The vacation was not found.");
            }

            // Formater les données de la vacation pour LabView
            return jsonStringLogWithVacationView(vacation);
        } catch (Exception error) {
            System.err.println("Error fetching log: " + error.getMessage());
            throw new RuntimeException("An error occurred while fetching the log.");
        }
    }

    private Map<String, Object> jsonStringLogWithVacationView(Vacation vacation) {
        Map<String, Object> dataLogger = new HashMap<>();

        dataLogger.put("id", vacation.getId());
        dataLogger.put("date", vacation.getDate());
        dataLogger.put("heure", formatTime(convertToLocalDateTime(new Date(vacation.getDate()))));
        dataLogger.put("isCabin", vacation.isLogout() ? 1 : 0);
        dataLogger.put("cabin_at", vacation.getDateStart() != null ? vacation.getDateStart() : LocalDateTime.now());
        dataLogger.put("isStart", vacation.getDateStart() != null ? 1 : 0);
        dataLogger.put("start_at", vacation.getDateStart() != null ? vacation.getDateStart() : LocalDateTime.now());
        dataLogger.put("refer", "null");
        dataLogger.put("is_sent", 0);
        dataLogger.put("statut", "start");
        dataLogger.put("deconnected_at", vacation.getDateEnd() != null ? vacation.getDateEnd() : "null");
        dataLogger.put("isDeconnected", vacation.isLogout() ? 1 : 0);
        dataLogger.put("accountability", 1);
        dataLogger.put("accountability_at", "null");
        dataLogger.put("percepteur", vacation.getPercepteur());
        dataLogger.put("voie", vacation.getVoie());
        dataLogger.put("vacation_id", vacation.getId());

        return dataLogger;
    }




    public Logs findLogsByPercepteurIsLogged(Long voieId) {

        return  logsRepository.findByVoieIdAndIsCabinTrue(voieId);
    }

    public Object logoutPercepteur(LogoutPercepteurDto logoutPercepteurDto) {
        String invalidCredentialsMessage = "Erreur interne !";
        try {
            // Récupérer le log correspondant au percepteur et à la voie
            Logs logout = this.findLogsByPercepteurLastLogin(logoutPercepteurDto.getPercepteurId(), logoutPercepteurDto.getVoieId());

            // Mettre à jour le log pour marquer la déconnexion
            logout.setDeconnected(true);
            logout.setDeconnectedAt(getCurrentDateTimeFormatted());
            logout.setStatut(LogingStatus.LOGOUT);
            logsRepository.save(logout);

            // Récupérer et mettre à jour la vacation associée
            Vacation vacation = vacationService.findVacationById(logoutPercepteurDto.getVacationId());
            vacation.setDateEnd(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            vacation.setLogout(true);
            this.vacationRepository.save(vacation);

            // Retourner les données formatées pour LabView
            return jsonStringFormatForLabViewLogWithVacation(logout, vacation);
        } catch (Exception error) {
            System.out.println(error);
            throw new RuntimeException(invalidCredentialsMessage);
        }
    }


    private Map<String, Object> jsonStringFormatForLabViewLogWithVacation(Logs logs, Vacation vacation) {
        Map<String, Object> dataLogger = new HashMap<>();

        dataLogger.put("id", logs.getId());
        dataLogger.put("date", logs.getDate());
        dataLogger.put("heure", logs.getHeure());
        dataLogger.put("isCabin", logs.isCabin() ? 1 : 0);
        dataLogger.put("cabin_at", logs.getCabinAt());
        dataLogger.put("isStart", logs.isStart() ? 1 : 0);
        dataLogger.put("start_at", logs.getStartAt() != null ? logs.getStartAt() : "null");
        dataLogger.put("refer", logs.getRefer());
        dataLogger.put("is_sent", logs.isSent() ? 1 : 0);
        dataLogger.put("statut", logs.getStatut().toString()); // Convertir l'enum en String
        dataLogger.put("deconnected_at", logs.getDeconnectedAt() != null ? logs.getDeconnectedAt() : "null");
        dataLogger.put("isDeconnected", logs.isDeconnected() ? 1 : 0);
        dataLogger.put("accountability", logs.isAccountability() ? 1 : 0);
        dataLogger.put("accountability_at", logs.getAccountabilityAt() != null ? logs.getAccountabilityAt() : "null");
        dataLogger.put("percepteur", logs.getPercepteur()); // Assurez-vous que Percepteur est sérialisable
        dataLogger.put("voie", logs.getVoie()); // Assurez-vous que Voie est sérialisable
        dataLogger.put("vacation_id", vacation.getId());

        return dataLogger;
    }


    private String formatTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        }
        return dateTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public LocalDateTime convertToLocalDateTime(Date date) {
        return date.toInstant() // Convertir Date en Instant
                .atZone(ZoneId.systemDefault()) // Associer une timezone
                .toLocalDateTime(); // Convertir en LocalDateTime
    }


    private Map<String, Object> exportPassword(Percepteur percepteur, String password) {
        try {
            // Préparer les données pour le template
            Map<String, Object> data = new HashMap<>();
            data.put("title", "Mot de passe Percepteur");
            data.put("devis", "FCFA");
            data.put("entrepise", "SAFER");
            data.put("date", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy à HH:mm:ss")));
            data.put("percepteur", percepteur);
            data.put("password", password);

            // Charger le template HTML
            File file = ResourceUtils.getFile("classpath:templates/pdf-password.hbs");
            String templateContent = new String(Files.readAllBytes(Paths.get(file.getPath())));

            // Générer le PDF (utilisez une bibliothèque comme Flying Saucer ou Apache PDFBox)
            byte[] pdfBuffer = generatePdf(templateContent, data);

            // Convertir le PDF en base64
            String base64Data = Base64.getEncoder().encodeToString(pdfBuffer);

            // Retourner la réponse
            Map<String, Object> response = new HashMap<>();
            response.put("response", base64Data);
            response.put("status", 200);
            return response;
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

    private byte[] generatePdf(String templateContent, Map<String, Object> data) {
        // Implémentez la logique pour générer un PDF à partir du template HTML et des données
        // Vous pouvez utiliser une bibliothèque comme Flying Saucer ou Apache PDFBox
        // Exemple simplifié :
        return new byte[0]; // Remplacez par la logique réelle
    }


    public Map<String, Object> infoPasswordVacation(Percepteur percepteur, String password) {
        // Créer un objet de réponse
        Map<String, Object> data = new HashMap<>();
        data.put("percepteur", percepteur);
        data.put("password", password);

        // Retourner la réponse avec un statut HTTP 200
        Map<String, Object> response = new HashMap<>();
        response.put("response", data);
        response.put("status", 200);
        return response;
    }



    public Object cabineWithLoginPercepteur(Long voieId) {
        return null;
    }

    public Object getVoieAndPercepteurLogin(String type, Long siteId) {

        return null;
    }


    public Object findLogByPercepteurDeconnectedCabin(Long userId) {
        try {
            // Récupérer le percepteur par son ID utilisateur
            Percepteur percepteur = percepteurService.findOne(userId);

            // Récupérer le dernier log associé au percepteur
            Optional<Logs> logsOptional = logsRepository.findTopByPercepteurIdOrderByIdDesc(percepteur.getId());

            if (logsOptional.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun log trouvé pour ce percepteur.");
            }

            Logs logs = logsOptional.get();

            // Vérifier le statut du log
            if (logs.getStatut() == LogingStatus.START) {
                return 301; // Percepteur est encore dans la voie
            }

            if (logs.getStatut() == LogingStatus.LOGGER) {
                return 302; // Percepteur n'a pas encore démarré la vacation
            }

            if (logs.getStatut() == LogingStatus.LOGOUT) {
                return logs; // Retourner les logs si le statut est LOGOUT
            }

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Statut du log non reconnu.");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Récupération d'un élément", e);
        }
    }

}
