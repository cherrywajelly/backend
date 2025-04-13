package com.timeToast.timeToast.dto.creatorAccount.response;

import com.timeToast.timeToast.domain.creatorAccount.CreatorAccount;
import lombok.Builder;

@Builder
public record CreatorAccountResponse (
        String bank,
        String accountNumber
){
    public static CreatorAccountResponse from(CreatorAccount creatorAccount, final String bank) {
        return CreatorAccountResponse.builder()
                .bank(bank)
                .accountNumber(creatorAccount.getAccountNumber())
                .build();
    }
}
