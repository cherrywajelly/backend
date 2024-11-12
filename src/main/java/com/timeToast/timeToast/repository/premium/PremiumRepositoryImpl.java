package com.timeToast.timeToast.repository.premium;

import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.global.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.PREMIUM_NOT_FOUND;

@Repository
public class PremiumRepositoryImpl implements PremiumRepository {

    private final PremiumJpaRepository premiumJpaRepository;

    public PremiumRepositoryImpl(final PremiumJpaRepository premiumJpaRepository) {
        this.premiumJpaRepository = premiumJpaRepository;
    }

    @Override
    public Premium getByPremiumType(final PremiumType premiumType){
        return premiumJpaRepository.getByPremiumType(premiumType);
    }
    @Override
    public Premium getById(final long premiumId) {
        return premiumJpaRepository.findById(premiumId).orElseThrow(()-> new NotFoundException(PREMIUM_NOT_FOUND.getMessage()));
    }

    @Override
    public List<Premium> getPremiums(){
        return premiumJpaRepository.findAll();
    }
}
