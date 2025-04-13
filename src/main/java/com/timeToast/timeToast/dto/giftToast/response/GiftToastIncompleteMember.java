package com.timeToast.timeToast.dto.giftToast.response;

import lombok.Builder;

@Builder
public record GiftToastIncompleteMember(
        long memberId,
        String nickname,
        boolean complete
) {
}
