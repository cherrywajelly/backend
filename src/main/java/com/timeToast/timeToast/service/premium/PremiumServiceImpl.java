package com.timeToast.timeToast.service.premium;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.premium.response.PremiumResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumResponses;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
@Service
public class PremiumServiceImpl implements PremiumService{

    private final PremiumRepository premiumRepository;
    private final MemberRepository memberRepository;

    public PremiumServiceImpl(final PremiumRepository premiumRepository, final MemberRepository memberRepository) {
        this.premiumRepository = premiumRepository;
        this.memberRepository = memberRepository;
    }


    @Transactional
    @Override
    public PremiumResponse savePremium(final long memberId, final long premiumId) {
        Member member = memberRepository.getById(memberId);
        member.updatePremiumId(premiumId);
        return PremiumResponse.from(premiumRepository.getById(premiumId));
    }

    @Transactional(readOnly = true)
    @Override
        public PremiumResponses getPremium() {
        List<PremiumResponse> premiumResponses = new ArrayList<>();
        premiumRepository.getPremiums().forEach(
                premium -> premiumResponses.add(PremiumResponse.from(premium)));
        return new PremiumResponses(premiumResponses);
    }
}
