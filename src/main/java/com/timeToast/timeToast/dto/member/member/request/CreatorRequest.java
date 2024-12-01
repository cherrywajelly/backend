package com.timeToast.timeToast.dto.member.member.request;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.dto.creator_account.response.CreatorAccountResponse;

public record CreatorRequest(
        String nickname,
        CreatorAccountResponse creatorAccountResponse
){
    public static CreatorAccount toCreatorAccount(CreatorRequest creatorRequest, final long creatorId) {
        return CreatorAccount.builder()
                .bank(creatorRequest.creatorAccountResponse.bank())
                .accountNumber(creatorRequest.creatorAccountResponse.accountNumber())
                .memberId(creatorId)
                .build();
    }
}
