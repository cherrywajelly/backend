package com.timeToast.timeToast.service.premium;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.premium.response.PremiumMonthlyRevenue;
import com.timeToast.timeToast.dto.premium.response.PremiumMonthlyRevenues;
import com.timeToast.timeToast.dto.premium.response.PremiumInfoResponse;
import com.timeToast.timeToast.dto.premium.response.PremiumInfoResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_YEAR_MONTH;

@Service
public class PremiumServiceImpl implements PremiumService{

    private final PremiumRepository premiumRepository;
    private final MemberRepository memberRepository;
    private final PaymentRepository paymentRepository;

    public PremiumServiceImpl(final PremiumRepository premiumRepository, final MemberRepository memberRepository,
                              final PaymentRepository paymentRepository) {
        this.premiumRepository = premiumRepository;
        this.memberRepository = memberRepository;
        this.paymentRepository = paymentRepository;
    }


    @Transactional
    @Override
    public PremiumInfoResponse savePremium(final long memberId, final long premiumId) {
        Member member = memberRepository.getById(memberId);
        member.updatePremiumId(premiumId);
        return PremiumInfoResponse.from(premiumRepository.getById(premiumId));
    }

    @Transactional(readOnly = true)
    @Override
        public PremiumInfoResponses getPremium() {
        List<PremiumInfoResponse> premiumInfoRespons = new ArrayList<>();
        premiumRepository.getPremiums().forEach(
                premium -> premiumInfoRespons.add(PremiumInfoResponse.from(premium)));
        return new PremiumInfoResponses(premiumInfoRespons);
    }

    @Override
    public PremiumMonthlyRevenues premiumMonthlyRevenue(int year) {
        if(year> LocalDate.now().getYear()){
            throw new BadRequestException(INVALID_YEAR_MONTH.getMessage());
        }
        List<PremiumMonthlyRevenue> premiumMonthlyRevenues = new ArrayList<>();

        for(int i=1; i<=LocalDate.now().getMonthValue(); i++){

            Long premium = paymentRepository.findPremiumPaymentSummaryDtoByYearMonth(year, i);
            if(premium == null){
                premium = 0L;
            }
            premiumMonthlyRevenues.add(PremiumMonthlyRevenue.builder()
                    .year(year)
                    .month(i)
                    .premiumCount(premium)
                    .build());
        }

        return new PremiumMonthlyRevenues(premiumMonthlyRevenues);
    }
}
