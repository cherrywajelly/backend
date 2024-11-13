package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupMarketResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;

import java.util.List;

public interface IconGroupService {
    void buyIconGroup(final long memberId, final long iconGroupId);
    List<IconGroupResponses> getToastIconGroups(final long memberId);
    List<IconGroupResponses> getJamIconGroups(final long memberId);
    IconGroupMarketResponses getAllToastsIconGroups(final long memberId);
    IconGroupMarketResponses getAllJamsIconGroups(final long memberId);
    IconGroupDetailResponse getIconGroupDetail(final long iconGroupId);
    void deleteIconGroup(final long memberId, final long iconGroupId);
}
