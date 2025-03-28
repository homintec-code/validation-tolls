package com.tgms.validationtolls.validations.service;

import com.tgms.validationtolls.validations.model.Validation;
import com.tgms.validationtolls.validations.repository.ValidationsRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.apache.kafka.common.requests.FetchMetadata.log;

@Service
@Transactional
@RequiredArgsConstructor
public class ValidationSyncService {
    private final ScheduledExecutorService scheduledExecutor;
    private final BlockingQueue<Validation> pendingSyncQueue = new LinkedBlockingQueue<>();
    private final ValidationsRepository validationRepository;
    private final KafkaTemplate<String, Validation> kafkaTemplate;

    private static final int MAX_ATTEMPTS = 5;
    private static final int INITIAL_DELAY = 1;
    private static final int MAX_DELAY = 3600;

    @PostConstruct
    public void init() {
        // Relancer les validations non synchronisées au démarrage
        validationRepository.findBySyncedFalse().forEach(pendingSyncQueue::add);

        // Démarrer le worker de synchronisation
        new Thread(this::syncWorker).start();
    }

    public void addValidation(Validation validation) {
        validation.setSynced(false);
        Validation saved = validationRepository.save(validation);
        pendingSyncQueue.add(saved);
    }

    private void syncWorker() {
        while (true) {
            try {
                Validation validation = pendingSyncQueue.take();
                attemptSync(validation);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void attemptSync(Validation validation) {
        try {
            kafkaTemplate.send("validations-topic", validation.getId().toString(), validation)
                    .get(10, TimeUnit.SECONDS); // Timeout configurable

            validation.setSynced(true);
            validationRepository.save(validation);
        } catch (Exception e) {
            handleSyncFailure(validation, e);
        }
    }

    private void handleSyncFailure(Validation validation, Exception e) {
        validation.setLastSyncAttempt(LocalDateTime.now());
        validation.setSyncAttemptCount(validation.getSyncAttemptCount() + 1);
        validationRepository.save(validation);

        // Réajouter à la file avec un délai exponentiel
        int delay = (int) Math.min(3600, Math.pow(2, validation.getSyncAttemptCount()));
        scheduledExecutor.schedule(() -> pendingSyncQueue.add(validation),
                delay, TimeUnit.SECONDS);
    }


    @Scheduled(fixedRate = 3600000) // Toutes les heures
    public void retryFailedSyncs() {
        List<Validation> failedSyncs = validationRepository.findBySyncedFalseAndLastSyncAttemptBefore(
                LocalDateTime.now().minusHours(1));

        failedSyncs.forEach(validation -> {
            if (validation.getSyncAttemptCount() < MAX_ATTEMPTS) {
                pendingSyncQueue.add(validation);
            } else {
                alertAdmin(validation);
            }
        });
    }

    private void alertAdmin(Validation validation) {
        String alertMessage = String.format(
                "ALERT: Échec de sync après %d tentatives - Validation ID %s",
                validation.getSyncAttemptCount(),
                validation.getId()
        );
        log.error(alertMessage);

        // Vous pouvez aussi écrire dans un fichier
        try {
            Files.writeString(
                    Path.of("sync_errors.log"),
                    LocalDateTime.now() + " - " + alertMessage + "\n",
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            log.error("Échec d'écriture dans le fichier de log", e);
        }
    }
}