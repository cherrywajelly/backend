package com.timeToast.timeToast.service.month_settlement;

import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.month_settlement.MonthSettlement;
import com.timeToast.timeToast.dto.month_settlement.request.MonthSettlementRequest;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementCreatorResponse;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementCreatorResponses;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponse;
import com.timeToast.timeToast.dto.month_settlement.response.MonthSettlementResponses;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.month_settlement.MonthSettlementRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class MonthSettlementServiceImpl implements MonthSettlementService {

    private final MonthSettlementRepository monthSettlementRepository;
    private final MemberRepository memberRepository;
    private final IconGroupRepository iconGroupRepository;
    private final IconRepository iconRepository;
    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    @Override
    public MonthSettlementCreatorResponses getCreatorMonthSettlements(final long creatorId) {
        List<MonthSettlement> monthSettlements = monthSettlementRepository.findAllByMemberId(creatorId);
        List<MonthSettlementCreatorResponse> monthSettlementResponses = new ArrayList<>();

        monthSettlements.forEach(monthSettlement -> {
            monthSettlementResponses.add(MonthSettlementCreatorResponse.from(monthSettlement));
        });
        return new MonthSettlementCreatorResponses(monthSettlementResponses);
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

//    @Transactional(readOnly = true)
//    @Override
//    public MonthSettlementDetailResponse getMonthSettlementDetail(final long memberId, final long monthSettlementId) {
//        Member member = memberRepository.getById(memberId);
//        MonthSettlement monthSettlement = monthSettlementRepository.getById(monthSettlementId);
//
//        List<IconGroupOrderedResponse> iconGroupOrderedResponses = new ArrayList<>();
//        List<IconGroup> iconGroups = iconGroupRepository.findAllByMemberId(memberId);
//        iconGroups.forEach(iconGroup -> {
//            List<Icon> icon = iconRepository.findAllByIconGroupId(iconGroup.getId());
//            List<String> iconImageUrls = new ArrayList<>();
//            icon.forEach(iconImage -> iconImageUrls.add(iconImage.getIconImageUrl()));
//
//            List<Payment> payments = paymentRepository.findAllByItemIdAndCreatedAtMonth(iconGroup.getId(), monthSettlement.getYearMonth().toString());
//            long income = payments.stream()
//                    .mapToLong(Payment::getAmount)
//                    .sum();
//
//            iconGroupOrderedResponses.add(IconGroupOrderedResponse.of(iconGroup.getName(), iconImageUrls, payments.size(), income));
//        });
//
//        long selledIconCount = iconGroupOrderedResponses.stream().mapToLong(IconGroupOrderedResponse::orderCount).sum();
//        return MonthSettlementDetailResponse.from(member, monthSettlement.getSettlement(), selledIconCount, new IconGroupOrderedResponses(iconGroupOrderedResponses));
//    }
}