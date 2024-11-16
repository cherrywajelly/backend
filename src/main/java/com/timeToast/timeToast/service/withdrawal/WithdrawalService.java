package com.timeToast.timeToast.service.withdrawal;

import com.timeToast.timeToast.global.response.Response;

public interface WithdrawalService {

    Response memberWithdrawal(final long memberId);
    Response creatorWithdrawal(final long memberId);
    Response adminWithdrawal(final long memberId);
}
