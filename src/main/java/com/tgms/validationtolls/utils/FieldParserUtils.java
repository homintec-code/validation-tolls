package com.tgms.validationtolls.utils;

public class FieldParserUtils {

    /**
     * Convert a field to a number, with a default value if the conversion fails or the value is "NAND".
     *
     * @param value        The value to parse.
     * @param defaultValue The default value to return if parsing fails.
     * @return The parsed number or the default value.
     */
    public static int parseNumericField(Object value, int defaultValue) {
        if (value == null || "NAND".equals(value)) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value.toString());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Handle string fields with default values.
     *
     * @param value        The value to parse.
     * @param defaultValue The default value to return if the value is "No defined" or "NAND".
     * @return The parsed string or the default value.
     */
    public static String parseStringField(Object value, String defaultValue) {
        if (value == null || "No defined".equals(value) || "NAND".equals(value)) {
            return defaultValue;
        }
        return value.toString();
    }

    /**
     * Convert a field to a boolean.
     *
     * @param value The value to parse.
     * @return true if the value is "1", false otherwise.
     */
    public static boolean parseBooleanField(Object value) {
        if (value == null) {
            return false;
        }
        try {
            return Integer.parseInt(value.toString()) == 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}