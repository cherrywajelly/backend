package com.timeToast.timeToast.dto.follow;

import com.timeToast.timeToast.domain.member.Member;
import lombok.Builder;

@Builder
public record FollowResponse(

        long memberId,
        String nickname,
        String memberProfileUrl
) {
    public static FollowResponse from(final Member member){
        return FollowResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .memberProfileUrl(member.getMemberProfileUrl())
                .build();
    }
}
