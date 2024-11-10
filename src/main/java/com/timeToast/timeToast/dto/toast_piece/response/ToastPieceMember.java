package com.timeToast.timeToast.dto.toast_piece.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record ToastPieceMember(
        long memberId,
        String nickname,
        String profileUrl
) {

    public static ToastPieceMember from(final Member member){
        return ToastPieceMember.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .profileUrl(member.getMemberProfileUrl())
                .build();
    }
}
