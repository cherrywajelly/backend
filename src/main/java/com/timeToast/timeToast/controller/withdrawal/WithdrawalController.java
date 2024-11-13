package com.timeToast.timeToast.controller.withdrawal;

import com.timeToast.timeToast.domain.member.member.LoginMember;
import com.timeToast.timeToast.global.annotation.Login;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class WithdrawalController {


    @DeleteMapping("/v1/withdrawal")
    public void memberWithdrawal(@Login final LoginMember loginMember){

    }

    @DeleteMapping("/v2/withdrawal")
    public void creatorWithdrawal(@Login final LoginMember loginMember){

    }

    @DeleteMapping("/v3/withdrawal")
    public void adminWithdrawal(@Login final LoginMember loginMember){

    }
}
