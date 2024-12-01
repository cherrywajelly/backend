package com.timeToast.timeToast.service.settlement;

import com.timeToast.timeToast.domain.enums.gift_toast.GiftToastType;
import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.timeToast.timeToast.domain.gift_toast.gift_toast.GiftToast;
import com.timeToast.timeToast.domain.settlement.Settlement;
import com.timeToast.timeToast.dto.settlement.request.SettlementApprovalRequest;
import com.timeToast.timeToast.dto.settlement.response.SettlementCreatorInfoResponse;
import com.timeToast.timeToast.dto.settlement.response.SettlementCreatorInfoResponses;
import com.timeToast.timeToast.repository.creator_account.CreatorAccountRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.settlement.SettlementRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SettlementServiceImplTest {

    @Mock
    SettlementRepository settlementRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    CreatorAccountRepository creatorAccountRepository;

    @Mock
    PaymentRepository paymentRepository;


    @InjectMocks
    SettlementServiceImpl settlementService;


    private List<Settlement> settlementsSetUp(){
        List<Settlement> settlements = new ArrayList<>();

        for (long i = 1; i < 10; i++) {
            Settlement settlement = Settlement.builder()
                    .memberId(1L)
                    .iconGroupId(1L)
                    .yearMonth(LocalDate.now())
                    .settlementState(SettlementState.APPROVAL)
                    .settlements(1000)
                    .revenue(1000)
                    .salesCount(10)
                    .build();
            ReflectionTestUtils.setField(settlement, "id", i);
            settlements.add(settlement);
        }
        return settlements;
    }

    private List<Settlement> settlementsByCreatorSetUp(){
        List<Settlement> settlements = new ArrayList<>();

        for (long i = 1; i < 10; i++) {
            Settlement settlement = Settlement.builder()
                    .memberId(1L)
                    .iconGroupId(1L)
                    .yearMonth(LocalDate.of(LocalDate.now().getYear(), (int) i,1))
                    .settlementState(SettlementState.APPROVAL)
                    .settlements(1000)
                    .revenue(1000)
                    .salesCount(10)
                    .build();
            ReflectionTestUtils.setField(settlement, "id", i);
            settlement.updateSettlementDate(LocalDate.of(LocalDate.now().getYear(), (int) i+1,1));
            settlements.add(settlement);
        }

        Settlement settlement = Settlement.builder()
                .memberId(1L)
                .iconGroupId(1L)
                .yearMonth(LocalDate.of(LocalDate.now().getYear(), 1,1))
                .settlementState(SettlementState.APPROVAL)
                .settlements(1000)
                .revenue(1000)
                .salesCount(10)
                .build();

        ReflectionTestUtils.setField(settlement, "id", 1L);
        settlement.updateSettlementDate(LocalDate.of(LocalDate.now().getYear(), 2,1));
        settlements.add(settlement);

        return settlements;
    }


    @Test
    @DisplayName("제작자 월 별 정산 승인 성공")
    public void approvalSettlement(){
        //given
        List<Settlement> settlements = settlementsSetUp();

        SettlementApprovalRequest settlementApprovalRequest = new SettlementApprovalRequest(1L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), SettlementState.APPROVAL);

        when(settlementRepository.findAllByYearMonthAndMemberId(
                LocalDate.of(settlementApprovalRequest.year(), settlementApprovalRequest.month(),1),1L)).thenReturn(settlements);


        //when
        SettlementCreatorInfoResponse creatorInfoResponse = settlementService.approvalSettlement(settlementApprovalRequest);

        //then
        assertEquals(creatorInfoResponse.month(), settlementApprovalRequest.month());
        assertEquals(creatorInfoResponse.year(), settlementApprovalRequest.year());
        assertEquals(creatorInfoResponse.settlementDate(), LocalDate.now());
    }

//    @Test
//    @DisplayName("제작자는 자신의 월 별 정산 내역 목록을 조회할 수 있다.")
//    public void getSettlementByYearMonthByCreator(){
//        //given
//        List<Settlement> settlements = settlementsByCreatorSetUp();
//
//        SettlementApprovalRequest settlementApprovalRequest = new SettlementApprovalRequest(1L, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), SettlementState.APPROVAL);
//
////        when(settlementRepository.findAllByYearMonthAndMemberId(
////                LocalDate.of(settlementApprovalRequest.year(), settlementApprovalRequest.month(),1),1L)).thenReturn(settlements);
//
//
//        //when
//        SettlementCreatorInfoResponses creatorInfoResponse = settlementService.getSettlementByYearMonthByCreator(1L);

        //then
//        assertEquals(creatorInfoResponse.month(), settlementApprovalRequest.month());
//        assertEquals(creatorInfoResponse.year(), settlementApprovalRequest.year());
//        assertEquals(creatorInfoResponse.settlementDate(), LocalDate.now());
//    }
}