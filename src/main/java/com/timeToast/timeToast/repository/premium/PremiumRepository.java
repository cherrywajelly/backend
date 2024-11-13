package com.timeToast.timeToast.repository.premium;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.premium.Premium;

import java.util.List;

public interface PremiumRepository {
    Premium getByPremiumType(final PremiumType premiumType);
    Premium getById(final long premiumId);
    List<Premium> getPremiums();
}
