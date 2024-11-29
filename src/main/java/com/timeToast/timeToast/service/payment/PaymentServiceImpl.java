package com.timeToast.timeToast.service.payment;


import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
import com.timeToast.timeToast.domain.enums.payment.ItemType;
import com.timeToast.timeToast.domain.enums.payment.PaymentState;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.domain.payment.Payment;
import com.timeToast.timeToast.domain.premium.Premium;
import com.timeToast.timeToast.dto.payment.*;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.config.TossConfig.TOSS_CONFIRM_URL;
import static com.timeToast.timeToast.global.config.TossConfig.TOSS_SECRET_KEY;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final IconGroupRepository iconGroupRepository;
    private final PremiumRepository premiumRepository;
    private final MemberRepository memberRepository;
    private final IconMemberRepository iconMemberRepository;




    public PaymentServiceImpl(final PaymentRepository paymentRepository, final IconGroupRepository iconGroupRepository,
                              final PremiumRepository premiumRepository, final MemberRepository memberRepository,
                              final IconMemberRepository iconMemberRepository) {
        this.paymentRepository = paymentRepository;
        this.iconGroupRepository = iconGroupRepository;
        this.premiumRepository = premiumRepository;
        this.memberRepository = memberRepository;
        this.iconMemberRepository = iconMemberRepository;
    }

    @Transactional
    @Override
    public PaymentSaveResponse savePayment(final long memberId, final PaymentSaveRequest paymentSaveRequest) {

        String orderName = verifyPaymentSaveRequest(memberId,paymentSaveRequest);

        Payment payment = PaymentSaveRequest.to(memberId, paymentSaveRequest);
        paymentRepository.save(payment);
        payment.updateOrderId(RandomStringUtils.randomAlphanumeric(6, 64));


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
        return new PaymentFailResponse(payment.getId(),payment.getOrderId(),"실패 했습니다.");
    }

    private String verifyPaymentSaveRequest(final long memberId, final PaymentSaveRequest paymentSaveRequest) {
        if(paymentSaveRequest.itemType().equals(ItemType.ICON)){
            IconGroup iconGroup = iconGroupRepository.getById(paymentSaveRequest.itemId());
            if(iconGroup.getPrice()!= paymentSaveRequest.amount() || iconGroup.getIconBuiltin().equals(IconBuiltin.BUILTIN)){
                throw new BadRequestException(INVALID_PAYMENT.getMessage());
            }

            if(iconMemberRepository.findByMemberIdAndIconGroupId(memberId, iconGroup.getId()).isPresent()){
                throw new BadRequestException(ICON_PAYMENT_EXISTS.getMessage());
            }
            return iconGroup.getName();

        }else{
            Premium premium = premiumRepository.getById(paymentSaveRequest.itemId());
            if(premium.getPrice()!= paymentSaveRequest.amount() ||  premium.getPremiumType().equals(PremiumType.BASIC)){
                throw new BadRequestException(INVALID_PAYMENT.getMessage());
            }

            if(memberRepository.getById(memberId).getPremiumId().equals(premium.getId())){
                throw new BadRequestException(PREMIUM_PAYMENT_EXISTS.getMessage());
            }
            return premium.getPremiumType().toString();
        }

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
        headers.setBasicAuth(Base64.getEncoder().encodeToString(TOSS_SECRET_KEY.getBytes(StandardCharsets.UTF_8)));
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentSuccessRequest> tossConfirmRequest = new HttpEntity<>(paymentSuccessRequest, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                TOSS_CONFIRM_URL,
                HttpMethod.POST,
                tossConfirmRequest,
                String.class
        );

        return response.getStatusCode() == HttpStatus.OK;
    }

}
