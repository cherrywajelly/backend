package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record MemberInfoResponse(
        String nickname,
        String profileUrl
) {

    public static MemberInfoResponse from(final Member member){
        return MemberInfoResponse.builder()
                .nickname(member.getNickname())
                .profileUrl(member.getMemberProfileUrl())
                .build();
    }
}
