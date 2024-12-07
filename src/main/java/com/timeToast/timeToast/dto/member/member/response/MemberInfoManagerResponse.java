package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record MemberInfoManagerResponse (
        long memberId,
        String memberProfileUrl,
        String nickname,
        String email,
        MemberRole memberRole,
        LoginType loginType,
        PremiumType premiumType
) {
    public static MemberInfoManagerResponse from(final Member member, final PremiumType premiumType) {
        return MemberInfoManagerResponse.builder()
                .memberId(member.getId())
                .memberProfileUrl(member.getMemberProfileUrl())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .memberRole(member.getMemberRole())
                .loginType(member.getLoginType())
                .premiumType(premiumType)
                .build();
    }
}
