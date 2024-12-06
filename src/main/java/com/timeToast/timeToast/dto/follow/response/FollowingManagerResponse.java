package com.timeToast.timeToast.dto.follow.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record FollowingManagerResponse(
        String followingMemberProfileUrl,
        String followingMemberNickname
) {
    public static FollowingManagerResponse from(final Member followingMember) {
        return FollowingManagerResponse.builder()
                .followingMemberProfileUrl(followingMember.getMemberProfileUrl())
                .followingMemberNickname(followingMember.getNickname())
                .build();
    }
}
