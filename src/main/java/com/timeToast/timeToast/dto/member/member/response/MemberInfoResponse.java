package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record MemberInfoResponse(
        long memberId,
        String nickname,
        String profileUrl
) {

    public static MemberInfoResponse from(final Member member){
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getMemberProfileUrl())
                .build();
    }
}
