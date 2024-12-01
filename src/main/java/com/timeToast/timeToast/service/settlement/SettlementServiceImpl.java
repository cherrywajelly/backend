package com.timeToast.timeToast.service.settlement;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.settlement.Settlement;
import com.timeToast.timeToast.dto.settlement.request.SettlementApprovalRequest;
import com.timeToast.timeToast.dto.settlement.request.SettlementDetailRequest;
import com.timeToast.timeToast.dto.settlement.request.SettlementRequest;
import com.timeToast.timeToast.dto.settlement.response.*;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.settlement.SettlementRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_CREATOR;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettlementServiceImpl implements SettlementService {
    private final SettlementRepository settlementRepository;
    private final MemberRepository memberRepository;
    private final CreatorAccountRepository creatorAccountRepository;
    private final PaymentRepository paymentRepository;

//    @Transactional(readOnly = true)
//    @Override
//    public SettlementCreatorResponses getCreatorMonthSettlements(final long creatorId) {
//        List<MonthSettlement> monthSettlements = settlementRepository.findAllByMemberId(creatorId);
//        List<SettlementCreatorResponse> monthSettlementResponses = new ArrayList<>();
//
//        monthSettlements.forEach(monthSettlement -> {
//            monthSettlementResponses.add(SettlementCreatorResponse.from(monthSettlement));
//        });
//        return new SettlementCreatorResponses(monthSettlementResponses);
//    }


    @Transactional
    @Override
    public SettlementCreatorInfoResponse approvalSettlement(final long creatorId, SettlementRequest settlementRequest) {
        settlementRepository.findAllByYearMonthAndMemberId(LocalDate.of(settlementRequest.year(), settlementRequest.month(),1), creatorId).forEach(
                        settlement -> {
                            settlement.updateSettlementState(SettlementState.APPROVAL);
                            settlement.updateSettlementDate(LocalDate.now());
                        }
        );
        return new SettlementCreatorInfoResponse(settlementRequest.year(), settlementRequest.month(), LocalDate.now());
    }

    @Transactional(readOnly = true)
    @Override
    public SettlementCreatorInfoResponses getSettlementByYearMonthByCreator(final long memberId) {
        List<SettlementCreatorInfoResponse> settlementCreatorInfoResponses = new ArrayList<>();
        settlementRepository.findAllByMemberId(memberId).stream().collect(Collectors.toMap(
                Settlement::getYearsMonth,
                response -> response,
                (existing, replacement) -> existing)).values().stream().toList().forEach(
                        settlementResponse -> {

                            if(settlementResponse.getSettlementState().equals(SettlementState.APPROVAL)){
                                settlementCreatorInfoResponses.add(
                                        SettlementCreatorInfoResponse.builder()
                                                .year(settlementResponse.getYearsMonth().getYear())
                                                .month(settlementResponse.getYearsMonth().getMonthValue())
                                                .settlementDate(settlementResponse.getSettlementDate())
                                                .build());
                            }
                        }

        );
        return new SettlementCreatorInfoResponses(settlementCreatorInfoResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public SettlementResponses getSettlementByYearMonth(final int year, final int month) {
        List<SettlementResponse> settlementResponses = new ArrayList<>();
        settlementRepository.findAllByYearMonth(LocalDate.of(year, month,1))
                .stream().collect(Collectors.toMap(
                        Settlement::getMemberId,
                        response -> response,
                        (existing, replacement) -> existing)).values().stream().toList().forEach(
                        monthSettlement -> {
                            Optional<Member> creator = memberRepository.findById(monthSettlement.getMemberId());

                            if(creator.isPresent()) {
                                settlementResponses.add(SettlementResponse.builder()
                                        .memberId(monthSettlement.getMemberId())
                                        .nickname(creator.get().getNickname())
                                        .profileUrl(creator.get().getMemberProfileUrl())
                                        .settlementState(SettlementState.WAITING)
                                        .build());
                            }
                        }
                );



        return new SettlementResponses(settlementResponses);
    }



    @Transactional(readOnly = true)
    @Override
    public SettlementDetailResponse getAllSettlementByCreator(final long memberId, final int year, final int month) {
        CreatorAccount creatorAccount = getCreatorAccount(memberId);

        List<SettlementIcon> settlementIcons =
                getMonthSettlementIcons(year, month, memberId);

        SettlementState settlementState;
        if(settlementIcons.isEmpty()){
            settlementState = SettlementState.WAITING;
        }else{
            settlementState = settlementIcons.stream().findFirst().get().settlementState();
        }

        return SettlementDetailResponse.builder()
                .year(year)
                .month(month)
                .creatorNickname(memberRepository.getById(memberId).getNickname())
                .salesIconCount(settlementIcons.stream().mapToLong(SettlementIcon::salesCount).sum())
                .totalRevenue(settlementIcons.stream().mapToLong(SettlementIcon::revenue).sum())
                .settlement((long) (settlementIcons.stream().mapToLong(SettlementIcon::revenue).sum()*0.7))
                .bank(creatorAccount.getBank().value())
                .accountNumber(creatorAccount.getAccountNumber())
                .settlementState(settlementState)
                .settlementIcons(settlementIcons)
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public SettlementDetailResponse getSettlementByYearMonthAndCreator(final long creatorId, final int year, final int month) {

        CreatorAccount creatorAccount = getCreatorAccount(creatorId);

        List<SettlementIcon> settlementIcons =
                getMonthSettlementIcons(year, month, creatorId);

        SettlementState settlementState;
        if(settlementIcons.isEmpty()){
            settlementState = SettlementState.WAITING;
        }else{
            settlementState = settlementIcons.stream().findFirst().get().settlementState();
        }

        return SettlementDetailResponse.builder()
                .year(year)
                .month(month)
                .creatorNickname(memberRepository.getById(creatorId).getNickname())
                .salesIconCount(settlementIcons.stream().mapToLong(SettlementIcon::salesCount).sum())
                .totalRevenue(settlementIcons.stream().mapToLong(SettlementIcon::revenue).sum())
                .settlement((long) (settlementIcons.stream().mapToLong(SettlementIcon::revenue).sum()*0.7))
                .bank(creatorAccount.getBank().value())
                .accountNumber(creatorAccount.getAccountNumber())
                .settlementState(settlementState)
                .build();
    }

    private CreatorAccount getCreatorAccount(final long monthSettlementDetailRequest) {
        Optional<CreatorAccount> creatorAccount = creatorAccountRepository.findByMemberId(monthSettlementDetailRequest);

        if (creatorAccount.isEmpty()) {
            throw new BadRequestException(INVALID_CREATOR.getMessage());
        }
        return creatorAccount.get();
    }

    private List<SettlementIcon> getMonthSettlementIcons(final int year, final int month, final long creatorId) {
        return settlementRepository.findAllByYearMonthAndMemberIdToIcon(
                LocalDate.of(year, month,1), creatorId);
    }


    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    public void updateMonthSettlement() {
        paymentRepository.findAllByMonthlyPayments(LocalDate.now().minusMonths(1), LocalDate.now()).forEach(
                paymentDto -> settlementRepository.save(
                        Settlement.builder()
                                .memberId(paymentDto.memberId())
                                .iconGroupId(paymentDto.itemId())
                                .yearMonth(LocalDate.now())
                                .revenue(paymentDto.salesRevenue())
                                .settlements((long) (paymentDto.salesRevenue()*0.7))
                                .settlementState(SettlementState.WAITING)
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