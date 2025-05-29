package com.timeToast.timeToast.dto.member.member.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record MemberProfileResponse(
        String nickname,
        String profileUrl,
        boolean isFollow
) {

    public static MemberProfileResponse from(final Member member, final Boolean isFollow){
        return MemberProfileResponse.builder()
                .nickname(member.getNickname())
                .profileUrl(member.getMemberProfileUrl())
                .isFollow(isFollow)
                .build();
    }
}
