package com.timeToast.timeToast.dto.gift_toast.response;

import com.timeToast.timeToast.domain.member.Member;
import lombok.Builder;

@Builder
public record GiftToastOwnerResponse(

        Long memberId,
        String nickname,
        Boolean inComplete
)
{
    public static GiftToastOwnerResponse from(final Member member, final boolean inComplete){
        return GiftToastOwnerResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .inComplete(inComplete)
                .build();
    }
}
