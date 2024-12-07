package com.timeToast.timeToast.service.payment;

import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.icon.icon_member.IconMember;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.dto.payment.request.PaymentSaveRequest;
import com.timeToast.timeToast.dto.payment.request.PaymentSuccessRequest;
import com.timeToast.timeToast.dto.payment.response.PaymentFailResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentSaveResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentSuccessResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentsAdminResponses;
import com.timeToast.timeToast.global.config.TossConfig;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    IconGroupRepository iconGroupRepository;

    @Mock
    PremiumRepository premiumRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    IconMemberRepository iconMemberRepository;

    @Mock
    TossConfig tossConfig;

    @InjectMocks
    PaymentServiceImpl paymentService;

    private Member setUpMember() {
        return Member.builder()
                .premiumId(1L)
                .email("test@gmail.com")
                .nickname("testNickname")
                .memberProfileUrl("testProfileUrl")
                .loginType(LoginType.GOOGLE)
                .memberRole(MemberRole.USER)
                .build();
    }

    private IconMember setUpIconMember() {
        return IconMember.builder()
                .iconGroupId(1L)
                .memberId(1L)
                .build();
    }

    private IconGroup setUpIconGroup() {
        return IconGroup.builder()
                .name("test icon group")
                .description("test icon group")
                .memberId(1L)
                .price(100)
                .iconType(IconType.TOAST)
                .iconBuiltin(IconBuiltin.NONBUILTIN)
                .build();
    }

    private Premium setPremium() {
        return Premium.builder()
                .premiumType(PremiumType.PREMIUM)
                .count(100)
                .price(100)
                .build();
    }

    private Payment setIconPayment() {
        return Payment.builder()
                .paymentState(PaymentState.WAITING)
                .memberId(1L)
                .amount(100)
                .itemId(1L)
                .itemType(ItemType.ICON)
                .build();
    }

    private Payment setPremiumPayment() {
        return Payment.builder()
                .paymentState(PaymentState.WAITING)
                .memberId(1L)
                .amount(100)
                .itemId(1L)
                .itemType(ItemType.PREMIUM)
                .build();
    }

    private Page<Payment> paymentSetUp() {
        List<Payment> paymentList = new ArrayList<>();

        for (long i = 1; i <= 10; i++) {
            Payment payment = Payment.builder()
                    .paymentState(PaymentState.WAITING)
                    .memberId(1L)
                    .amount(100)
                    .itemId(1L)
                    .itemType(ItemType.PREMIUM)
                    .build();
            ReflectionTestUtils.setField(payment, "id", i);
            ReflectionTestUtils.setField(payment, "createdAt", LocalDateTime.now());
            paymentList.add(payment);
        }

        return new PageImpl<>(paymentList, PageRequest.of(0, 5), paymentList.size());
    }

    @Test
    @DisplayName("사용자는 결제 정보를 저장할 수 있다. - 성공 : 아이콘 그룹")
    public void saveIconPaymentSuccessTest() throws Exception {
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member,"id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        IconMember iconMember = setUpIconMember();
        ReflectionTestUtils.setField(iconMember,"id", 1L);
        when(iconMemberRepository.findByMemberIdAndIconGroupId(1L, 1L)).thenReturn(Optional.empty());

        IconGroup iconGroup = setUpIconGroup();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);
        when(iconGroupRepository.getById(1L)).thenReturn(iconGroup);

        Payment payment = setIconPayment();
        ReflectionTestUtils.setField(payment, "id", 1L);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1L, 100,
                ItemType.ICON, "success url", "fail url" );

        //when
        PaymentSaveResponse paymentSaveResponse = paymentService.savePayment(1L, paymentSaveRequest);

        //then
        assertNotNull(paymentSaveResponse.orderId());
        assertEquals(member.getEmail(), paymentSaveResponse.customerEmail());
        assertEquals(iconGroup.getName(), paymentSaveResponse.orderName());
        assertEquals(paymentSaveRequest.successUrl(), paymentSaveResponse.successUrl());
        assertEquals(paymentSaveRequest.failUrl(), paymentSaveResponse.failUrl());
        verify(paymentRepository, times(1)).save(any(Payment.class));

    }


    @Test
    @DisplayName("사용자는 결제 정보를 저장할 수 있다. - 실패: 아이콘 이미 결제한 아이콘 그룹")
    public void savePaymentFailExistsTest() throws Exception {
        //given
        IconMember iconMember = setUpIconMember();
        ReflectionTestUtils.setField(iconMember,"id", 1L);
        when(iconMemberRepository.findByMemberIdAndIconGroupId(1L, 1L)).thenReturn(Optional.of(iconMember));

        IconGroup iconGroup = setUpIconGroup();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);
        when(iconGroupRepository.getById(1L)).thenReturn(iconGroup);


        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1L, 100,
                ItemType.ICON, "success url", "fail url" );

        //when then
        assertThrows(BadRequestException.class, () -> paymentService.savePayment(1L, paymentSaveRequest) );

    }

    @Test
    @DisplayName("사용자는 결제 정보를 저장할 수 있다. - 실패: 아이콘 가격 불일치")
    public void savePaymentFailAmountTest() throws Exception {
        //given
        IconMember iconMember = setUpIconMember();
        ReflectionTestUtils.setField(iconMember,"id", 1L);

        IconGroup iconGroup = setUpIconGroup();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);
        when(iconGroupRepository.getById(1L)).thenReturn(iconGroup);

        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1L, 150,
                ItemType.ICON, "success url", "fail url" );

        //when then
        assertThrows(BadRequestException.class, () -> paymentService.savePayment(1L, paymentSaveRequest) );

    }

    @Test
    @DisplayName("사용자는 결제 정보를 저장할 수 있다. - 성공 : 프리미엄")
    public void savePremiumPaymentSuccessTest() throws Exception {
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member,"id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        Premium premium = setPremium();
        ReflectionTestUtils.setField(premium,"id", 2L);
        when(premiumRepository.getById(2L)).thenReturn(premium);

        Payment payment = setPremiumPayment();
        ReflectionTestUtils.setField(payment, "id", 1L);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(2L, 100,
                ItemType.PREMIUM, "success url", "fail url" );

        //when
        PaymentSaveResponse paymentSaveResponse = paymentService.savePayment(1L, paymentSaveRequest);

        //then
        assertNotNull(paymentSaveResponse.orderId());
        assertEquals(member.getEmail(), paymentSaveResponse.customerEmail());
        assertEquals(premium.getPremiumType().toString(), paymentSaveResponse.orderName());
        assertEquals(paymentSaveRequest.successUrl(), paymentSaveResponse.successUrl());
        assertEquals(paymentSaveRequest.failUrl(), paymentSaveResponse.failUrl());
        verify(paymentRepository, times(1)).save(any(Payment.class));

    }

    @Test
    @DisplayName("사용자는 결제 정보를 저장할 수 있다. - 실패: 이미 결제한 프리미엄")
    public void savePremiumPaymentFailExistsTest() throws Exception {
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member,"id", 1L);
        when(memberRepository.getById(1L)).thenReturn(member);

        Premium premium = setPremium();
        ReflectionTestUtils.setField(premium,"id", 1L);
        when(premiumRepository.getById(1L)).thenReturn(premium);

        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(1L, 100,
                ItemType.PREMIUM, "success url", "fail url" );

        //when then
        assertThrows(BadRequestException.class, () -> paymentService.savePayment(1L, paymentSaveRequest) );

    }

    @Test
    @DisplayName("사용자는 결제 정보를 저장할 수 있다. - 실패: 프리미엄 가격 불일치")
    public void savePremiumPaymentFailAmountTest() throws Exception {
        //given
        Member member = setUpMember();
        ReflectionTestUtils.setField(member,"id", 1L);

        Premium premium = setPremium();
        ReflectionTestUtils.setField(premium,"id", 2L);
        when(premiumRepository.getById(2L)).thenReturn(premium);

        PaymentSaveRequest paymentSaveRequest = new PaymentSaveRequest(2L, 150,
                ItemType.PREMIUM, "success url", "fail url" );

        //when then
        assertThrows(BadRequestException.class, () -> paymentService.savePayment(1L, paymentSaveRequest) );

    }

    @Test
    @DisplayName("사용자는 성공한 결제를 승인 요청 할 수 있다.:실패 금액 불일치")
    public void successPaymentFail() throws Exception {
        //given
        Payment payment = setPremiumPayment();
        ReflectionTestUtils.setField(payment, "id", 1L);
        ReflectionTestUtils.setField(payment, "orderId", "jdhaeudjkioeudjc");
        when(paymentRepository.findByOrderId("jdhaeudjkioeudjc")).thenReturn(Optional.of(payment));

        PaymentSuccessRequest paymentSuccessRequest = new PaymentSuccessRequest("paymentKey", "jdhaeudjkioeudjc", 55 );
        //when then
        assertThrows(BadRequestException.class,()->paymentService.successPayment(1L, paymentSuccessRequest));

    }

    @Test
    @DisplayName("사용자는 성공한 결제를 승인 요청 할 수 있다.: 주문 번호 불일치")
    public void successPaymentOrderIdFail() throws Exception {
        //given
        Payment payment = setPremiumPayment();
        ReflectionTestUtils.setField(payment, "id", 1L);
        ReflectionTestUtils.setField(payment, "orderId", "jdhaeudjkioeudjc");

        PaymentSuccessRequest paymentSuccessRequest = new PaymentSuccessRequest("paymentKey", "jdhaeudsxioeudjc", 55 );
        //when then
        assertThrows(NotFoundException.class,()->paymentService.successPayment(1L, paymentSuccessRequest));

    }


    @Test
    @DisplayName("사용자는 실패 결제를 저장할 수 있다.")
    public void failPaymentTest() throws Exception {
        //given
        Payment payment = setPremiumPayment();
        ReflectionTestUtils.setField(payment, "id", 1L);
        ReflectionTestUtils.setField(payment, "orderId", "jdhaeudjkioeudjc");
        when(paymentRepository.findByOrderId("jdhaeudjkioeudjc")).thenReturn(Optional.of(payment));

        //when
        PaymentFailResponse paymentFailResponse = paymentService.failPayment(1L, "jdhaeudjkioeudjc");

        //then
        assertEquals(payment.getId(), paymentFailResponse.paymentId());
    }

    @Test
    @DisplayName("사용자는 실패 결제를 저장할 수 있다.: 실패 정보 찾을 수 없음")
    public void failPaymentTestFail() throws Exception {
        //given when then
        assertThrows(NotFoundException.class, () -> paymentService.failPayment(1L, "jdhaeudjkioeudjc"));
    }


    @Test
    @DisplayName("관리자는 모든 아이콘 관련 결제를 조회할 수 있다.")
    public void getIconPayments() throws Exception {
        //given

        Page<Payment> payments = paymentSetUp();
        when(paymentRepository.findAll(any(Pageable.class))).thenReturn(payments);

        Member member = setUpMember();
        ReflectionTestUtils.setField(member,"id", 1L);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        IconGroup iconGroup = setUpIconGroup();
        ReflectionTestUtils.setField(iconGroup, "id", 1L);
        when(iconGroupRepository.getById(anyLong())).thenReturn(iconGroup);

        //when
        PaymentsAdminResponses paymentsAdminResponses = paymentService.getIconPayments(0,5);
        //then
        assertEquals(10, paymentsAdminResponses.paymentsAdminResponses().size());
    }

    @Test
    @DisplayName("관리자는 모든 프리미엄 결제를 조회할 수 있다.")
    public void getPremiumPayments() throws Exception {
        //given

        Page<Payment> payments = paymentSetUp();
        when(paymentRepository.findAll(any(Pageable.class))).thenReturn(payments);

        Member member = setUpMember();
        ReflectionTestUtils.setField(member,"id", 1L);
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));

        Premium premium = setPremium();
        ReflectionTestUtils.setField(premium,"id", 2L);

        //when
        PaymentsAdminResponses paymentsAdminResponses = paymentService.getPremiumPayments(0,5);
        //then
        assertEquals(10, paymentsAdminResponses.paymentsAdminResponses().size());
    }


}
