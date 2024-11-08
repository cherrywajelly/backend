package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupResponses;

import java.util.List;

public interface IconGroupService {
    void buyIconGroup(long memberId, long iconGroupId);
    List<IconGroupResponses> getIconGroups(long memberId);
}
