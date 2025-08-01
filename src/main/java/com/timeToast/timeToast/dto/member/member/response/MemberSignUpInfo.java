package com.timeToast.timeToast.dto.member.member.response;

import lombok.Builder;

@Builder
public record MemberSignUpInfo(
        long totalUserCount,
        long totalCreatorCount
) {
}
