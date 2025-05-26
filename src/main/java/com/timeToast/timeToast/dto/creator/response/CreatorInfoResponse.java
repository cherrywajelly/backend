package com.timeToast.timeToast.dto.creator.response;

import com.timeToast.timeToast.domain.enums.creator_account.Bank;
import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record CreatorInfoResponse(
        String nickname,
        Bank bank,
        String accountNumber,
        String profileUrl
) {
    public static CreatorInfoResponse from(Member member) {
        return CreatorInfoResponse.builder()
                .nickname(member.getNickname())
                .bank(member.getBank())
                .accountNumber(member.getAccountNumber())
                .profileUrl(member.getMemberProfileUrl())
                .build();
    }
}
