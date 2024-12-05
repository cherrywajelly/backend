package com.timeToast.timeToast.dto.follow.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record FollowManagerResponse (
        String followMemberProfileUrl,
        String followMemberNickname
) {
    public static FollowManagerResponse from(final Member followMember) {
        return FollowManagerResponse.builder()
                .followMemberProfileUrl(followMember.getMemberProfileUrl())
                .followMemberNickname(followMember.getNickname())
                .build();
    }
}
