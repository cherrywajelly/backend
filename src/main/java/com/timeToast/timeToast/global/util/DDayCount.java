package com.timeToast.timeToast.global.util;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public record DDayCount (
        LocalDate now,
        LocalDate endDate
){


    public static long count(final LocalDate now, final LocalDate endDate){
        return DAYS.between(now,endDate);
    }
}
