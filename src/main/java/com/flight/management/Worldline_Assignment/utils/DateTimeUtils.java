package com.flight.management.Worldline_Assignment.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {
    public static LocalDateTime convertToUTC(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        ZonedDateTime cetTime = localDateTime.atZone(ZoneId.of("CET"));
        return cetTime.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
    }

    public static LocalDateTime convertToCET(LocalDateTime localDateTime) {
        if (localDateTime == null) return null;
        ZonedDateTime utcTime = localDateTime.atZone(ZoneId.of("UTC"));
        return utcTime.withZoneSameInstant(ZoneId.of("CET")).toLocalDateTime();
    }
}
