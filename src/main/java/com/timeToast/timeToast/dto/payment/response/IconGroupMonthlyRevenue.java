package com.timeToast.timeToast.dto.payment.response;

import lombok.Builder;

@Builder
public record IconGroupMonthlyRevenue(
        int year,
        int month,
        long toastsRevenue,
        long jamsRevenue
) {
}
