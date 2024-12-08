package com.timeToast.timeToast.dto.member.member.response;

import lombok.Builder;

@Builder
public record MemberSummaryResponse(
        long totalUserCount,
        long totalCreatorCount

) {
}
