package com.timeToast.timeToast.service.settlement;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.monthSettlement.SettlementState;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.settlement.Settlement;
import com.timeToast.timeToast.dto.settlement.request.SettlementRequest;
import com.timeToast.timeToast.dto.settlement.response.*;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

    private List<Settlement> settlementsByAdminSetup(){
        List<Settlement> settlements = new ArrayList<>();

        for(long i = 1; i < 5; i++) {
            settlements.add(Settlement.builder()
                            .memberId(i)
                            .salesCount(10)
                            .settlementState(SettlementState.APPROVAL)
                            .settlements(100)
                            .revenue(100)
                            .yearMonth(LocalDate.now())
                            .iconGroupId(i)
                            .build());
        }

        settlements.add(Settlement.builder()
                .memberId(1L)
                .salesCount(10)
                .settlementState(SettlementState.APPROVAL)
                .settlements(100)
                .revenue(100)
                .yearMonth(LocalDate.now())
                .iconGroupId(2L)
                .build());

        return settlements;
    }

    private Member memberSetUp(final long id){
        Member member = Member.builder()
                .email("email")
                .memberRole(MemberRole.CREATOR)
                .nickname("nickname")
                .memberProfileUrl("memberProfileUrl")
                .build();

        ReflectionTestUtils.setField(member, "id", id);

        return member;
    }

    private CreatorAccount creatorAccountSetUp(){
        return CreatorAccount.builder()
                .memberId(1L)
                .bank(Bank.IBK)
                .accountNumber("accountNumber")
                .build();
    }

    private List<SettlementIcon> settlementIconSetUp(){
        List<SettlementIcon> settlementIcons = new ArrayList<>();

        for(long i = 1; i < 10; i++) {
            settlementIcons.add(
                    SettlementIcon.builder()
                            .title("title"+i)
                            .revenue(100L)
                            .salesCount(12)
                            .settlementState(SettlementState.APPROVAL)
                            .build());

        }

        return settlementIcons;
    }

    @Test
    @DisplayName("제작자 월 별 정산 승인 성공")
    public void approvalSettlement(){
        //given
        List<Settlement> settlements = settlementsSetUp();

        when(settlementRepository.findAllByYearMonthAndMemberId(
                LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonthValue(),1),1L)).thenReturn(settlements);

        SettlementRequest settlementRequest = new SettlementRequest(LocalDate.now().getYear(),LocalDate.now().getMonthValue() );

        //when
        SettlementCreatorInfoResponse creatorInfoResponse = settlementService.approvalSettlement(1L, settlementRequest);

        //then
        assertEquals(creatorInfoResponse.month(), LocalDate.now().getMonthValue());
        assertEquals(creatorInfoResponse.year(),  LocalDate.now().getYear());
        assertEquals(creatorInfoResponse.settlementDate(), LocalDate.now());
    }

    @Test
    @DisplayName("제작자는 자신의 월 별 정산 내역 목록을 조회할 수 있다.: 성공 - 중복 제거 확인")
    public void getSettlementByYearMonthByCreator(){
        //given
        List<Settlement> settlements = settlementsByCreatorSetUp();
        final long memberId = 1L;
        when(settlementRepository.findAllByMemberId(memberId)).thenReturn(settlements);

        //when
        SettlementCreatorInfoResponses creatorInfoResponse = settlementService.getSettlementByYearMonthByCreator(1L);

        //then
        assertEquals(settlements.size()-1, creatorInfoResponse.settlementCreatorInfoResponses().size());
    }

    @Test
    @DisplayName("관리자는 선택한 년도와 월에 대하여 정산 목록 조회를 할 수 있다.")
    public void getSettlementByYearMonth(){
        //given
        List<Settlement> settlements = settlementsByAdminSetup();
        when(settlementRepository.findAllByYearMonth(any(LocalDate.class))).thenReturn(settlements);

        Member member1 = memberSetUp(1L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member1));
        Member member2 = memberSetUp(2L);
        when(memberRepository.findById(2L)).thenReturn(Optional.of(member2));
        Member member3 = memberSetUp(3L);
        when(memberRepository.findById(3L)).thenReturn(Optional.of(member3));
        Member member4 = memberSetUp(4L);
        when(memberRepository.findById(4L)).thenReturn(Optional.of(member4));

        //when
        SettlementResponses settlementResponses = settlementService.getSettlementByYearMonth(LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        //then
        assertEquals(settlements.size()-1, settlementResponses.settlementResponses().size());
    }

    @Test
    @DisplayName("관리자는 선택한 년도와 월에 대하여 정산 목록 조회를 할 수 있다.")
    public void getAllSettlementByCreator(){
        //given
        Member member = memberSetUp(1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        CreatorAccount creatorAccount = creatorAccountSetUp();
        when(creatorAccountRepository.findByMemberId(1L)).thenReturn(Optional.of(creatorAccount));

        List<SettlementIcon> settlementIcons = settlementIconSetUp();
        when(settlementRepository.findAllByYearMonthAndMemberIdToIcon(any(LocalDate.class), eq(1L))).thenReturn(settlementIcons);

        //when
        SettlementDetailResponse settlementDetailResponse = settlementService.getAllSettlementByCreator(member.getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        //then
        assertEquals(LocalDate.now().getYear(), settlementDetailResponse.year());
        assertEquals(LocalDate.now().getMonthValue(), settlementDetailResponse.month());
        assertEquals(member.getNickname(), settlementDetailResponse.creatorNickname());
        assertEquals(settlementIcons.stream().mapToLong(SettlementIcon::salesCount).sum(), settlementDetailResponse.salesIconCount());
        assertEquals(settlementIcons.stream().mapToLong(SettlementIcon::revenue).sum(), settlementDetailResponse.totalRevenue());
        assertEquals((long) (settlementIcons.stream().mapToLong(SettlementIcon::revenue).sum()*0.7), settlementDetailResponse.settlement());
        assertEquals(creatorAccount.getBank().value(), settlementDetailResponse.bank());
        assertEquals(creatorAccount.getAccountNumber(), settlementDetailResponse.accountNumber());
        assertEquals(SettlementState.APPROVAL, settlementDetailResponse.settlementState());
        assertEquals(settlementIcons.size(), settlementDetailResponse.settlementIcons().size());
    }


    @Test
    @DisplayName("관리자는 선택한 년도와 월에 대하여 특정 제작자의 정산 목록 조회를 할 수 있다.")
    public void getSettlementByYearMonthAndCreator(){
        //given
        Member member = memberSetUp(1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        CreatorAccount creatorAccount = creatorAccountSetUp();
        when(creatorAccountRepository.findByMemberId(1L)).thenReturn(Optional.of(creatorAccount));

        List<SettlementIcon> settlementIcons = settlementIconSetUp();
        when(settlementRepository.findAllByYearMonthAndMemberIdToIcon(any(LocalDate.class), eq(1L))).thenReturn(settlementIcons);

        //when
        SettlementDetailResponse settlementDetailResponse = settlementService.getAllSettlementByCreator(member.getId(), LocalDate.now().getYear(), LocalDate.now().getMonthValue());

        //then
        assertEquals(LocalDate.now().getYear(), settlementDetailResponse.year());
        assertEquals(LocalDate.now().getMonthValue(), settlementDetailResponse.month());
        assertEquals(member.getNickname(), settlementDetailResponse.creatorNickname());
        assertEquals(settlementIcons.stream().mapToLong(SettlementIcon::salesCount).sum(), settlementDetailResponse.salesIconCount());
        assertEquals(settlementIcons.stream().mapToLong(SettlementIcon::revenue).sum(), settlementDetailResponse.totalRevenue());
        assertEquals((long) (settlementIcons.stream().mapToLong(SettlementIcon::revenue).sum()*0.7), settlementDetailResponse.settlement());
        assertEquals(creatorAccount.getBank().value(), settlementDetailResponse.bank());
        assertEquals(creatorAccount.getAccountNumber(), settlementDetailResponse.accountNumber());
        assertEquals(SettlementState.APPROVAL, settlementDetailResponse.settlementState());
        assertEquals(settlementIcons.size(), settlementDetailResponse.settlementIcons().size());
    }

}