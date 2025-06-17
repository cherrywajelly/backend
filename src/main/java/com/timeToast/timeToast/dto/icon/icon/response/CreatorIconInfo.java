package com.timeToast.timeToast.dto.icon.icon.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CreatorIconInfo(
        String title,
        int income,
        int salesCount,
        List<String> iconImageUrl
) {
}
