package com.timeToast.timeToast.dto.gift_toast.response;

import com.timeToast.timeToast.domain.member.member.Member;
import lombok.Builder;

@Builder
public record GiftToastOwnerResponse(

        Long memberId,
        String nickname
)
{
    public static GiftToastOwnerResponse from(final Member member){
        return GiftToastOwnerResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
    }
}
