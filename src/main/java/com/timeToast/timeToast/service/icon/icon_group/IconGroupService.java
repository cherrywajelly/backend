package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupResponses;
import com.timeToast.timeToast.global.response.Response;

public interface IconGroupService {
    IconGroupResponses getToastIconGroups(final long memberId);
    IconGroupResponses getJamIconGroups(final long memberId);
    IconGroupMarketResponses getAllToastsIconGroups(final long memberId);
    IconGroupMarketResponses getAllJamsIconGroups(final long memberId);
    IconGroupMarketDetailResponse getIconGroupDetail(final long memberId, final long iconGroupId);
    Response deleteIconGroup(final long memberId, final long iconGroupId);
}
