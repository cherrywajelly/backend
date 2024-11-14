package com.timeToast.timeToast.dto.creator.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record CreatorResponse(
        long memberId,
        String profileUrl,
        String nickname
) {

    public static CreatorResponse from(final Member member) {
        return CreatorResponse.builder()
                .memberId(member.getId())
                .profileUrl(member.getMemberProfileUrl())
                .nickname(member.getNickname())
                .build();
    }

    public String getNickname() {
        return nickname;
    }
}