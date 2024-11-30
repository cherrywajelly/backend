package com.timeToast.timeToast.service.monthSettlement;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import com.timeToast.timeToast.dto.month_settlement.request.MonthSettlementRequest;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponse;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponses;
import com.timeToast.timeToast.dto.payment.PaymentDto;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.monthSettlement.MonthSettlementRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MonthSettlementServiceImpl implements MonthSettlementService {

    private final MonthSettlementRepository monthSettlementRepository;
    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public MonthSettlementServiceImpl(final MonthSettlementRepository monthSettlementRepository,final PaymentRepository paymentRepository,
                                      final MemberRepository memberRepository) {
        this.monthSettlementRepository = monthSettlementRepository;
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public MonthSettlementResponses getMonthSettlementByYearMonth(final MonthSettlementRequest monthSettlementRequest) {
        List<MonthSettlementResponse> monthSettlementResponses = new ArrayList<>();
        monthSettlementRepository.findAllByYearMonth(LocalDate.of(monthSettlementRequest.year(), monthSettlementRequest.month(),1))
                .stream().collect(Collectors.toMap(
                        MonthSettlement::getMemberId,
                        response -> response,
                        (existing, replacement) -> existing
                )).values().stream().toList().forEach(
                monthSettlement -> {
                    Optional<Member> creator = memberRepository.findById(monthSettlement.getMemberId());

                    if(creator.isPresent()) {
                        monthSettlementResponses.add(MonthSettlementResponse.builder()
                                .memberId(monthSettlement.getMemberId())
                                .nickname(creator.get().getNickname())
                                .profileUrl(creator.get().getMemberProfileUrl())
                                .settlementState(SettlementState.BEFORE_SETTLEMENT)
                                .build());
                    }
                }
        );



        return new MonthSettlementResponses(monthSettlementResponses);
    }



    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void updateMonthSettlement() {
        paymentRepository.findAllByMonthlyPayments(LocalDate.now().minusMonths(1), LocalDate.now()).forEach(
                paymentDto -> monthSettlementRepository.save(
                            MonthSettlement.builder()
                                    .memberId(paymentDto.memberId())
                                    .iconGroupId(paymentDto.itemId())
                                    .yearMonth(LocalDate.now())
                                    .revenue(paymentDto.salesRevenue())
                                    .settlement((long) (paymentDto.salesRevenue()*0.7))
                                    .settlementState(SettlementState.BEFORE_SETTLEMENT)
                                    .build()));

    }


}
