package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record MemberManagerResponse(
        long memberId,
        String memberProfileUrl,
        String nickname
) {
    public static MemberManagerResponse from(final Member member) {
        return MemberManagerResponse.builder()
                .memberId(member.getId())
                .memberProfileUrl(member.getMemberProfileUrl())
                .nickname(member.getNickname())
                .build();
    }
}
