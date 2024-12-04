package com.timeToast.timeToast.controller.payment;

import com.timeToast.timeToast.dto.payment.response.PaymentDetailResponse;
import com.timeToast.timeToast.dto.payment.response.PaymentsAdminResponses;
import com.timeToast.timeToast.service.payment.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3/payments")
public class PaymentAdminController {
    private final PaymentService paymentService;

    public PaymentAdminController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("")
    public PaymentsAdminResponses getPayments( @RequestParam("size") int size,
                                               @RequestParam("page") int page) {
        return paymentService.getPayments(page,size);
    }

    @GetMapping("/{paymentId}")
    public PaymentDetailResponse getPaymentDetails(@PathVariable("paymentId") long paymentId) {
        return paymentService.getPaymentDetails(paymentId);
    }
}
