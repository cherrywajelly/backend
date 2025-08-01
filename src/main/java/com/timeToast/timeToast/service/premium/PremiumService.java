package com.timeToast.timeToast.service.premium;

import com.timeToast.timeToast.dto.premium.response.PremiumMonthlyRevenues;
import com.timeToast.timeToast.dto.premium.response.PremiumInfoResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumInfoResponses;

public interface PremiumService {
    PremiumInfoResponse savePremium(final long memberId, final long premiumId);
    PremiumInfoResponses getPremium();
    PremiumMonthlyRevenues premiumMonthlyRevenue(final int year);
}
