package com.timeToast.timeToast.controller.withdrawal;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.global.annotation.Login;
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
    public void memberWithdrawal(@Login final LoginMember loginMember){
        withdrawalService.memberWithdrawal(loginMember.id());
    }

    @DeleteMapping("/v2/withdrawal")
    public void creatorWithdrawal(@Login final LoginMember loginMember){
        withdrawalService.creatorWithdrawal(loginMember.id());
    }

    @DeleteMapping("/v3/withdrawal")
    public void adminWithdrawal(@Login final LoginMember loginMember){
        withdrawalService.adminWithdrawal(loginMember.id());
    }
}
