package com.timeToast.timeToast.service.premium;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.dto.premium.response.PremiumMonthlyRevenue;
import com.timeToast.timeToast.dto.premium.response.PremiumMonthlyRevenues;
import com.timeToast.timeToast.dto.premium.response.PremiumInfoResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumInfoResponses;

import java.util.*;

public class PremiumServiceTest implements PremiumService {

    @Override
    public PremiumInfoResponse savePremium(final long memberId, final long premiumId) {
        return new PremiumInfoResponse(1L, PremiumType.BASIC, 0, 0, "description");
    }

    @Override
    public PremiumInfoResponses getPremium() {
        List<PremiumInfoResponse> premiumInfoRespons = new ArrayList<>();
        premiumInfoRespons.add(new PremiumInfoResponse(1L, PremiumType.BASIC, 0, 0, "description"));
        return new PremiumInfoResponses(premiumInfoRespons);
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