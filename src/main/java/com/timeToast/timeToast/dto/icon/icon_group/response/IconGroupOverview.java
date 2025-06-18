package com.timeToast.timeToast.dto.icon.icon_group.response;

import com.timeToast.timeToast.domain.enums.icon_group.IconState;
import com.timeToast.timeToast.domain.icon.icon_group.IconGroup;
import com.timeToast.timeToast.dto.icon.icon.IconResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record IconGroupOverview(
        IconGroupSummaryInfo iconGroupSummaryInfo,
        IconState iconState,
        List<IconResponse> icons,

        //TODO 분리할 수 있으면 하기
        long orderCount,
        long income

) {
    public static IconGroupOverview from(final IconGroupSummaryInfo iconGroupSummaryInfo,
                                               final IconGroup iconGroup,
                                               final int orderCount, final long income) {
        return IconGroupOverview.builder()
                .iconGroupSummaryInfo(iconGroupSummaryInfo)
                .iconState(iconGroup.getIconState())
                .icons(iconGroup.getIcons().stream().map(IconResponse::from).toList())
                .orderCount(orderCount)
                .income(income)
                .build();
    }
}
