package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.enums.member.LoginType;
import com.timeToast.timeToast.domain.enums.member.MemberRole;
import com.timeToast.timeToast.domain.enums.premium.PremiumType;
import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record MemberManagerResponse(
        MemberInfoResponse memberInfoResponse,
        String email,
        MemberRole memberRole,
        LoginType loginType,
        PremiumType premiumType
) {
    public static MemberManagerResponse from(final Member member, final PremiumType premiumType) {

        return MemberManagerResponse.builder()
                .memberInfoResponse(MemberInfoResponse.from(member))
                .memberRole(member.getMemberRole())
                .loginType(member.getLoginType())
                .premiumType(premiumType)
                .build();
    }
}
