package com.timeToast.timeToast.dto.creator_account.response;

import com.timeToast.timeToast.domain.creator_account.CreatorAccount;
import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import lombok.Builder;

@Builder
public record CreatorAccountResponse (
        Bank bank,
        String accountNumber
){
    public static CreatorAccountResponse from(CreatorAccount creatorAccount) {
        return CreatorAccountResponse.builder()
                .bank(creatorAccount.getBank())
                .accountNumber(creatorAccount.getAccountNumber())
                .build();
    }
}
