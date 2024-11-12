package com.timeToast.timeToast.service.premium;

import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponses;

public interface PremiumService {
    PremiumResponse savePremium(final long memberId, final long premiumId);
    PremiumResponses getPremium();

}
