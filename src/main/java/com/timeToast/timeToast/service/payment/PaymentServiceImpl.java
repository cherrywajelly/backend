package com.timeToast.timeToast.service.payment;


import com.timeToast.timeToast.domain.enums.icon_group.IconBuiltin;
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
import com.timeToast.timeToast.dto.payment.response.*;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.repository.icon.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.icon.icon_member.IconMemberRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.payment.PaymentRepository;
import com.timeToast.timeToast.repository.premium.PremiumRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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
    public PaymentSuccessResponse successPayment(final long memberId, final PaymentSuccessRequest paymentSuccessRequest) {
        Payment payment = verifyAmount(paymentSuccessRequest);
        if(!tossConfirm(paymentSuccessRequest)){
            throw new BadRequestException(INVALID_PAYMENT.getMessage());
        }
        payment.updatePaymentState(PaymentState.SUCCESS);

        String orderName;
        if(payment.getItemType().equals(ItemType.ICON)){
            iconMemberRepository.save(iconMemberRepository.save(IconMember.builder()
                    .memberId(memberId)
                    .iconGroupId(payment.getItemId())
                    .build()));
            orderName = iconGroupRepository.getById(payment.getItemId()).getName();
        }else{
            Member member = memberRepository.getById(memberId);
            member.updatePremiumId(payment.getItemId());
            orderName = premiumRepository.getById(payment.getItemId()).getPremiumType().toString();
            payment.updateExpiredDate(LocalDate.now().plusMonths(1));
        }

        return PaymentSuccessResponse.builder()
                .orderId(payment.getOrderId())
                .paymentId(payment.getId())
                .orderName(orderName)
                .build();
    }

    @Transactional
    @Override
    public PaymentFailResponse failPayment(final long memberId, final String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(
                () -> new NotFoundException(PAYMENT_NOT_FOUND.getMessage()));

        payment.updatePaymentState(PaymentState.FAILURE);
        return new PaymentFailResponse(payment.getId(),payment.getOrderId(),"실패 했습니다.");
    }

    @Override
    public PaymentsAdminResponses getPayments(final int page, final int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<PaymentsAdminResponse> paymentsResponse = new ArrayList<>();

        paymentRepository.findAll(pageRequest).forEach(
                payment -> {
                    String nickname = null;
                    Optional<Member> member = memberRepository.findById(payment.getMemberId());
                    if(member.isPresent()){
                        nickname = member.get().getNickname();
                    }

                    if(payment.getItemType().equals(ItemType.ICON)){
                        paymentsResponse.add(
                                PaymentsAdminResponse.builder()
                                        .createdAt(payment.getCreatedAt().toLocalDate())
                                        .paymentId(payment.getId())
                                        .itemName(iconGroupRepository.getById(payment.getItemId()).getName())
                                        .itemType(ItemType.ICON)
                                        .nickname(nickname)
                                        .amount(payment.getAmount())
                                        .paymentState(payment.getPaymentState())
                                        .build());
                    }else{
                        paymentsResponse.add(
                                PaymentsAdminResponse.builder()
                                        .createdAt(payment.getCreatedAt().toLocalDate())
                                        .paymentId(payment.getId())
                                        .itemName(premiumRepository.getById(payment.getItemId()).getPremiumType().toString())
                                        .itemType(ItemType.PREMIUM)
                                        .nickname(nickname)
                                        .amount(payment.getAmount())
                                        .paymentState(payment.getPaymentState())
                                        .expiredDate(payment.getExpiredDate())
                                        .build());
                    }
                }
        );

        return new PaymentsAdminResponses(paymentsResponse);
    }

    @Override
    public PaymentDetailResponse getPaymentDetails(final long paymentId) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(()-> new NotFoundException(PAYMENT_NOT_FOUND.getMessage()));

        if(payment.getItemType().equals(ItemType.ICON)){
            IconGroup iconGroup = iconGroupRepository.getById(payment.getItemId());
            Optional<Member> member = memberRepository.findById(payment.getMemberId());
            String nickname = null;
            if(member.isPresent()){
                nickname = member.get().getNickname();
            }
            return PaymentDetailResponse.builder()
                    .orderId(payment.getOrderId())
                    .nickname(nickname)
                    .itemType(payment.getItemType())
                    .itemName(iconGroup.getName())
                    .amount(payment.getAmount())
                    .paymentState(payment.getPaymentState())
                    .createdAt(payment.getCreatedAt().toLocalDate())
                    .iconThumbnailImageUrl(iconGroup.getThumbnailImageUrl())
                    .build();
        }else{
            Premium premium = premiumRepository.getById(payment.getItemId());
            Optional<Member> member = memberRepository.findById(payment.getMemberId());
            String nickname = null;
            if(member.isPresent()){
                nickname = member.get().getNickname();
            }
            return PaymentDetailResponse.builder()
                    .orderId(payment.getOrderId())
                    .nickname(nickname)
                    .itemType(payment.getItemType())
                    .itemName(premium.getPremiumType().toString())
                    .amount(payment.getAmount())
                    .paymentState(payment.getPaymentState())
                    .createdAt(payment.getCreatedAt().toLocalDate())
                    .expiredDate(payment.getExpiredDate())
                    .build();
        }

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

    private Payment verifyAmount(final PaymentSuccessRequest paymentSuccessRequest) {
        Payment payment = paymentRepository.findByOrderId(paymentSuccessRequest.orderId()).orElseThrow(
                () -> new NotFoundException(PAYMENT_NOT_FOUND.getMessage()));

        if(payment.getAmount() != paymentSuccessRequest.amount()){
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
