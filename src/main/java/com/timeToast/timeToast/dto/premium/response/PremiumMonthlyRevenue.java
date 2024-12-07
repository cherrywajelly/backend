package com.timeToast.timeToast.dto.premium.response;

import lombok.Builder;

@Builder
public record PremiumMonthlyRevenue(
        int year,
        int month,
        long premiumCount
) {
}
