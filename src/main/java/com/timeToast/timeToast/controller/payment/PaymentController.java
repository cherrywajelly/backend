package com.timeToast.timeToast.controller.payment;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.dto.payment.request.PaymentSaveRequest;
import com.timeToast.timeToast.dto.payment.request.PaymentSuccessRequest;
import com.timeToast.timeToast.dto.payment.response.PaymentFailResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentSaveResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentSuccessResponse;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.service.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("")
    public PaymentSaveResponse postPayment(@Login LoginMember loginMember, @RequestBody PaymentSaveRequest paymentSaveRequest) {
        return paymentService.savePayment(loginMember.id(), paymentSaveRequest);
    }

    @PostMapping("/success")
    public PaymentSuccessResponse successPayment(@Login LoginMember loginMember,
                                                 @RequestBody PaymentSuccessRequest paymentSuccessRequest) {
        return paymentService.successPayment(loginMember.id(),paymentSuccessRequest);
    }

    @PostMapping("/fail")
    public PaymentFailResponse failPayment(@Login LoginMember loginMember, @RequestParam("orderId") final String orderId ) {
        return paymentService.failPayment(loginMember.id(), orderId);

    }


}
