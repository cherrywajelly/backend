package com.timeToast.timeToast.repository.premium;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.premium.Premium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PremiumJpaRepository extends JpaRepository<Premium, Long> {

    Premium getByPremiumType(final PremiumType premiumType);
}
