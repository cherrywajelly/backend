package com.timeToast.timeToast.domain.member;

import com.timeToast.timeToast.domain.enums.member.MemberRole;
import lombok.Builder;
import lombok.Getter;

@Builder
public record LoginMember(
        long id,
        String email,
        MemberRole role
) {
    public static LoginMember from(final Member member) {
        return LoginMember.builder()
                .id(member.getId())
                .email(member.getEmail())
                .role(member.getMemberRole())
                .build();
    }
}
