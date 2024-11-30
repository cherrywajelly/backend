package com.timeToast.timeToast.dto.creator.response;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import lombok.Builder;

@Builder
public record CreatorInfoResponse(
        String nickname,
        Bank bank,
        String accountNumber,
        String profileUrl
) {
    public static CreatorInfoResponse from(String nickname, Bank bank, String accountNumber, String profileUrl) {
        return CreatorInfoResponse.builder()
                .nickname(nickname)
                .bank(bank)
                .accountNumber(accountNumber)
                .profileUrl(profileUrl)
                .build();
    }
}
