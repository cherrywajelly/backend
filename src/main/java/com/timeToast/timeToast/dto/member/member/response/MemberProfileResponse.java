package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record MemberProfileResponse(
        String nickname,
        String profileUrl,
        long followingCount,
        long followerCount,
        long teamCount,
        boolean isFollow

) {

    public static MemberProfileResponse from(final Member member,final long followingCount,
                                             final long followerCount, final long  teamCount, final Boolean isFollow){
        return MemberProfileResponse.builder()
                .nickname(member.getNickname())
                .profileUrl(member.getMemberProfileUrl())
                .followingCount(followingCount)
                .followerCount(followerCount)
                .teamCount(teamCount)
                .isFollow(isFollow)
                .build();
    }
}
