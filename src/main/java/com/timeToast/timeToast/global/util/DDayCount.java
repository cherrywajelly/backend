package com.timeToast.timeToast.global.util;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public record DDayCount (
        LocalDate now,
        LocalDate endDate
){


    public static long count(final LocalDate now, final LocalDate endDate){
        long dDay = DAYS.between(now,endDate);

        if(dDay < 0){
            dDay = 0;
        }
        return dDay;
    }
}
