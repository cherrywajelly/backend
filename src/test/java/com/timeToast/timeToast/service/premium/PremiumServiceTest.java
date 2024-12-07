package com.timeToast.timeToast.service.premium;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.dto.premium.response.PremiumMonthlyRevenue;
import com.timeToast.timeToast.dto.premium.response.PremiumMonthlyRevenues;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponses;

import java.util.*;

public class PremiumServiceTest implements PremiumService {

    @Override
    public PremiumResponse savePremium(final long memberId, final long premiumId) {
        return new PremiumResponse(1L, PremiumType.BASIC, 0, 0, "description");
    }

    @Override
    public PremiumResponses getPremium() {
        List<PremiumResponse> premiumResponses = new ArrayList<>();
        premiumResponses.add(new PremiumResponse(1L, PremiumType.BASIC, 0, 0, "description"));
        return new PremiumResponses(premiumResponses);
    }

    @Override
    public PremiumMonthlyRevenues premiumMonthlyRevenue(final int year) {
        List<PremiumMonthlyRevenue> premiumMonthlyRevenues = new ArrayList<>();
        premiumMonthlyRevenues.add(
                new PremiumMonthlyRevenue(2024, 11, 100)
        );
        return new PremiumMonthlyRevenues(premiumMonthlyRevenues);
    }
}