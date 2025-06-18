package com.timeToast.timeToast.dto.icon.icon.response;

import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupOverview;
import lombok.Builder;

import java.util.List;

@Builder
public record CreatorProfileResponse (
        List<IconGroupOverview> iconGroupOverviews,
        long totalIconCount,
        long totalOrderCount,
        long totalIncome,
        long totalSettlement
) {
    public static CreatorProfileResponse from(List<IconGroupOverview> iconGroupOverviews) {
        return CreatorProfileResponse.builder()
                .iconGroupOverviews(iconGroupOverviews)
                .totalIconCount(iconGroupOverviews.stream().count())
                .totalOrderCount(iconGroupOverviews.stream().mapToLong(IconGroupOverview::orderCount).sum())
                .totalIncome(iconGroupOverviews.stream().mapToLong(IconGroupOverview::income).sum())
                //TODO
                .totalSettlement((long) (iconGroupOverviews.stream().mapToLong(IconGroupOverview::income).sum() * 0.7))
                .build();
    }
}
