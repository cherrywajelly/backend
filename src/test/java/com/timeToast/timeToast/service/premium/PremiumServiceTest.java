package com.timeToast.timeToast.service.premium;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponses;

import java.util.*;

public class PremiumServiceTest implements PremiumService {

    @Override
    public PremiumResponse savePremium(long memberId, long premiumId) {
        return new PremiumResponse(1L, PremiumType.BASIC, 0, 0, "description");
    }

    @Override
    public PremiumResponses getPremium() {
        List<PremiumResponse> premiumResponses = new ArrayList<>();
        premiumResponses.add(new PremiumResponse(1L, PremiumType.BASIC, 0, 0, "description"));
        return new PremiumResponses(premiumResponses);
    }
}