package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.premium.response.MemberPremium;
import lombok.Builder;

@Builder
public record MemberInfoResponse(
        long memberId,
        String nickname,
        String profileUrl,
        String email,
        MemberRole memberRole,
        LoginType loginType,
        MemberPremium memberPremium
) {

    public static MemberInfoResponse from(final Member member, MemberPremium memberPremium) {
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getMemberProfileUrl())
                .email(member.getEmail())
                .memberRole(member.getMemberRole())
                .loginType(member.getLoginType())
                .memberPremium(memberPremium)
                .build();
    }
}
