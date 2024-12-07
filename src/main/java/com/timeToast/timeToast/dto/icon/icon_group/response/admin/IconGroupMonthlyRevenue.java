package com.timeToast.timeToast.dto.icon.icon_group.response.admin;

import lombok.Builder;

@Builder
public record IconGroupMonthlyRevenue(
        int year,
        int month,
        long toastsRevenue,
        long jamsRevenue
) {
}
