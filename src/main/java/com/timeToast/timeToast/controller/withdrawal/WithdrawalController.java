package com.timeToast.timeToast.controller.withdrawal;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.global.annotation.Login;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.service.withdrawal.WithdrawalService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class WithdrawalController {

    private final WithdrawalService withdrawalService;

    public WithdrawalController(final WithdrawalService withdrawalService) {
        this.withdrawalService = withdrawalService;
    }

    @DeleteMapping("/v1/withdrawal")
    public Response memberWithdrawal(@Login final LoginMember loginMember){
        return withdrawalService.memberWithdrawal(loginMember.id());
    }

    @DeleteMapping("/v2/withdrawal")
    public Response creatorWithdrawal(@Login final LoginMember loginMember){
        return withdrawalService.creatorWithdrawal(loginMember.id());
    }

    @DeleteMapping("/v3/withdrawal")
    public Response adminWithdrawal(@Login final LoginMember loginMember){
        return withdrawalService.adminWithdrawal(loginMember.id());
    }
}
