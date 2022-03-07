package com.tamazian.util;

import lombok.experimental.UtilityClass;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@UtilityClass
public class LocalDateAndTimeFormatter {

    private static final String PATTERN = "MMMM d, yyyy HH:mm:ss";
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public LocalDateTime format(String date) {
        return LocalDateTime.parse(date, FORMATTER);
    }

    public boolean isValid(String date) {
        try {
            return Optional.ofNullable(date)
                    .map(LocalDateAndTimeFormatter::format)
                    .isPresent();
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
