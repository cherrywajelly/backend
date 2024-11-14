package com.timeToast.timeToast.service.withdrawal;

public interface WithdrawalService {

    void memberWithdrawal(final long memberId);
    void creatorWithdrawal(final long memberId);
    void adminWithdrawal(final long memberId);
}
