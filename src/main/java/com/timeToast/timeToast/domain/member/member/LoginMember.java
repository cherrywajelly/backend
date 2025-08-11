package com.timeToast.timeToast.domain.member.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import lombok.Builder;

@Builder
public record LoginMember(
        long id,
        MemberRole role
) {
    public static LoginMember from(final Member member) {
        return LoginMember.builder()
                .id(member.getId())
                .role(member.getMemberRole())
                .build();
    }
}
