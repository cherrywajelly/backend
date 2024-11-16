package com.timeToast.timeToast.service.withdrawal;

import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.constant.SuccessConstant;
import com.timeToast.timeToast.global.response.Response;

public class WithdrawalServiceImplTest implements WithdrawalService{

    @Override
    public Response memberWithdrawal(long memberId) {
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.SUCCESS_DELETE.getMessage());
    }

    @Override
    public Response creatorWithdrawal(long memberId) {
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.SUCCESS_DELETE.getMessage());
    }

    @Override
    public Response adminWithdrawal(long memberId) {
        return new Response(StatusCode.OK.getStatusCode(), SuccessConstant.SUCCESS_DELETE.getMessage());
    }
}