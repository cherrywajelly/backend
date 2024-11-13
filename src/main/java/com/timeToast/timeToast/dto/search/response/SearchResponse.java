package com.timeToast.timeToast.dto.search.response;

import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.service.icon.icon.IconService;
import lombok.Builder;

@Builder
public record SearchResponse(
        long memberId,
        String nickname,
        String profileUrl

) {

    public static SearchResponse from(final Member member){

        return SearchResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getMemberProfileUrl())
                .build();
    }
}
