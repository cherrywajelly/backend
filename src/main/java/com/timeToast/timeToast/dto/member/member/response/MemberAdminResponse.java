package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record MemberAdminResponse(
        long memberId,
        String memberProfileUrl,
        String nickname,
        MemberRole memberRole
) {
    public static MemberAdminResponse from(final Member member) {
        return MemberAdminResponse.builder()
                .memberId(member.getId())
                .memberProfileUrl(member.getMemberProfileUrl())
                .nickname(member.getNickname())
                .memberRole(member.getMemberRole())
                .build();
    }
}
