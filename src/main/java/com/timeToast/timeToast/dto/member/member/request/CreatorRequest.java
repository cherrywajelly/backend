package com.timeToast.timeToast.dto.member.member.request;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.member.member.Member;

public record CreatorRequest (
        String nickname,
        Bank bank,
        String accountNumber
) {
    public static CreatorAccount toCreatorAccount (CreatorRequest creatorRequest, final long memberId) {
        return CreatorAccount.builder()
                .bank(creatorRequest.bank)
                .accountNumber(creatorRequest.accountNumber)
                .memberId(memberId)
                .build();
    }
}
