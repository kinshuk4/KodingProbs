package com.n26.utils;

import java.time.Clock;
import java.time.LocalDateTime;

public final class DateUtils {

    private DateUtils() {

    }

    public static Long getEpocTime(LocalDateTime localDateTime) {
        return localDateTime.atZone(Clock.systemUTC().getZone()).toInstant().toEpochMilli();
    }

}
