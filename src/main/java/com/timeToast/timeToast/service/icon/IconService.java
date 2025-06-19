package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.dto.icon.response.UserIconGroupDetail;
import com.timeToast.timeToast.dto.icon.response.IconGroupInfoResponses;
import com.timeToast.timeToast.dto.icon.response.UserIconGroupDetailResponses;
import com.timeToast.timeToast.global.response.Response;

public interface IconService {
    UserIconGroupDetailResponses getToastIconGroupsByUser(final long memberId);
    UserIconGroupDetailResponses getJamIconGroupsByUser(final long memberId);
    IconGroupInfoResponses getAllToastsIconGroups(final long memberId);
    IconGroupInfoResponses getAllJamsIconGroups(final long memberId);
    UserIconGroupDetail getIconGroupDetail(final long memberId, final long iconGroupId);
    Response deleteIconGroup(final long memberId, final long iconGroupId);
}
