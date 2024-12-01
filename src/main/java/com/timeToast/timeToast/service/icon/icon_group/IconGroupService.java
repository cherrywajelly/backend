package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.creator.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon_group.response.*;
import com.timeToast.timeToast.global.response.Response;

import java.util.List;

public interface IconGroupService {
    IconGroupResponses getToastIconGroups(final long memberId);
    IconGroupResponses getJamIconGroups(final long memberId);
    IconGroupMarketResponses getAllToastsIconGroups(final long memberId);
    IconGroupMarketResponses getAllJamsIconGroups(final long memberId);
    IconGroupMarketDetailResponse getIconGroupDetail(final long iconGroupId);
    Response deleteIconGroup(final long memberId, final long iconGroupId);
}
