package com.tgms.validationtolls.utils;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class UtilHelper {


    /**
     * Génère une chaîne de chiffres de la longueur spécifiée.
     *
     * @param length La longueur de la chaîne de chiffres à générer.
     * @return Une chaîne de chiffres de la longueur spécifiée.
     */
    public static String generateDigits(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("La longueur doit être supérieure à 0.");
        }

        int add = 1;
        int max = 12 - add;

        // Si la longueur demandée est supérieure à max, on divise le problème
        if (length > max) {
            return generateDigits(max) + generateDigits(length - max);
        }

        // Calculer la plage pour le nombre aléatoire
        max = (int) Math.pow(10, length + add);
        int min = max / 10;

        // Générer un nombre aléatoire dans la plage [min, max]
        Random random = new Random();
        int number = random.nextInt(max - min + 1) + min;

        // Convertir en chaîne et supprimer le premier caractère
        return String.valueOf(number).substring(add);
    }

    public static String getCurrentDateTimeFormatted() {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentTime.format(formatter);
    }

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
