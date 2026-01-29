package utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    // Time formatter for HH:mm format
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    // Operating hours
    private static final LocalTime OPENING = LocalTime.of(8, 0);
    private static final LocalTime CLOSING = LocalTime.of(22, 0);

    public static boolean isValidUsername(String username) {
        return username != null && username.length() >= 3 && username.matches("^[a-zA-Z0-9_]+$");
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() >= 4;
    }

    public static boolean isValidResourceId(String resourceId) {
        return resourceId != null && !resourceId.trim().isEmpty();
    }

    public static boolean isValidCapacity(int capacity) {
        return capacity > 0 && capacity <= 1000;
    }

    public static boolean isFutureOrPresentDate(LocalDate date) {
        return date != null && !date.isBefore(LocalDate.now());
    }

    /**
     * Validates time range strings in HH:mm format.
     * Checks: start < end and within operating hours (08:00–22:00)
     */
    public static boolean isValidTimeRange(String startTimeStr, String endTimeStr) {
        LocalTime start = parseTime(startTimeStr);
        LocalTime end = parseTime(endTimeStr);

        if (start == null || end == null) return false;
        if (!start.isBefore(end)) return false;
        return !start.isBefore(OPENING) && !end.isAfter(CLOSING);
    }

    /**
     * Parses a time string into LocalTime. Returns null if invalid.
     */
    public static LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.isEmpty()) return null;

        try {
            return LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
