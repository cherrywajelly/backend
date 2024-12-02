package com.timeToast.timeToast.dto.creator_account.response;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.creator_account.Bank;
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
