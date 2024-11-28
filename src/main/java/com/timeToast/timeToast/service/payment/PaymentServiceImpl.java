package com.timeToast.timeToast.service.payment;


import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.dto.payment.*;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final IconGroupRepository iconGroupRepository;
    private final PremiumRepository premiumRepository;
    private final MemberRepository memberRepository;

    @Value("${payment.toss.secret-key}")
    private String tossSecretKey;

    @Value("${payment.toss.confirm-url}")
    private String tossConfirmUrl;


    public PaymentServiceImpl(final PaymentRepository paymentRepository, final IconGroupRepository iconGroupRepository,
                              final PremiumRepository premiumRepository, final MemberRepository memberRepository) {
        this.paymentRepository = paymentRepository;
        this.iconGroupRepository = iconGroupRepository;
        this.premiumRepository = premiumRepository;
        this.memberRepository = memberRepository;

    }

    @Transactional
    @Override
    public PaymentSaveResponse savePayment(final long memberId, final PaymentSaveRequest paymentSaveRequest) {
        Payment payment = PaymentSaveRequest.to(memberId, paymentSaveRequest);
        paymentRepository.save(payment);
        payment.updateOrderId(RandomStringUtils.randomAlphanumeric(6, 64));

        String orderName;
        if(payment.getItemType().equals(ItemType.ICON)){
            orderName = iconGroupRepository.getById(payment.getItemId()).getName();
        }else{
            orderName = premiumRepository.getById(payment.getItemId()).getPremiumType().toString();
        }

        //TODO 결제 가능 확인 코드 넣기
        return PaymentSaveResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .orderName(orderName)
                .successUrl(paymentSaveRequest.successUrl())
                .failUrl(paymentSaveRequest.failUrl())
                .customerEmail(memberRepository.getById(memberId).getEmail())
                .build();
    }

    @Transactional
    @Override
    public PaymentSuccessResponse successPayment(final long memberId, final long paymentId,
                                                 final PaymentSuccessRequest paymentSuccessRequest) {
        Payment payment = verifyAmount(paymentSuccessRequest.amount(), paymentId);
        if(!tossConfirm(paymentSuccessRequest)){
            throw new BadRequestException(INVALID_PAYMENT.getMessage());
        }
        payment.updatePaymentState(PaymentState.SUCCESS);

        String orderName;
        if(payment.getItemType().equals(ItemType.ICON)){
            orderName = iconGroupRepository.getById(payment.getItemId()).getName();
        }else{
            orderName = premiumRepository.getById(payment.getItemId()).getPremiumType().toString();
        }

        return PaymentSuccessResponse.builder()
                .orderId(payment.getOrderId())
                .paymentId(payment.getId())
                .orderName(orderName)
                .build();
    }

    @Transactional
    @Override
    public PaymentFailResponse failPayment(final long memberId, final long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new NotFoundException(PAYMENT_NOT_FOUND.getMessage()));

        payment.updatePaymentState(PaymentState.FAILURE);
        return null;
    }

    private Payment verifyAmount(final long amount, final long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(
                () -> new NotFoundException(PAYMENT_NOT_FOUND.getMessage()));

        if(payment.getAmount() != amount){
            throw new BadRequestException(INVALID_PAYMENT.getMessage());
        }
        return payment;
    }

    private boolean tossConfirm(final PaymentSuccessRequest paymentSuccessRequest) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();


        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(Base64.getEncoder().encodeToString(tossSecretKey.getBytes(StandardCharsets.UTF_8)));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentSuccessRequest> tossConfirmRequest = new HttpEntity<>(paymentSuccessRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                tossConfirmUrl,
                HttpMethod.POST,
                tossConfirmRequest,
                String.class
        );

        return response.getStatusCode() == HttpStatus.OK;
    }

}
