package com.timeToast.timeToast.dto.gift_toast.response;

import lombok.Builder;

@Builder
public record GiftToastIncompleteMember(
        long memberId,
        String nickname,
        boolean complete
) {
}
