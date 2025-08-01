package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.domain.enums.icon_group.IconType;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupDetail;
import com.timeToast.timeToast.dto.icon.response.MarketIconGroupResponses;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupResponses;
import com.timeToast.timeToast.global.response.Response;

public interface IconService {
    UserIconGroupResponses getUserIconGroups(final long memberId, final IconType iconType);
    MarketIconGroupResponses getMarketIconGroups(final long memberId, final IconType iconType);
    UserIconGroupDetail getIconGroupDetail(final long memberId, final long iconGroupId);
    Response deleteIconGroup(final long memberId, final long iconGroupId);
}
