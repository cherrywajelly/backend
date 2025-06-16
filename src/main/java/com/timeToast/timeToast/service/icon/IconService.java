package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupDetail;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupInfoResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.member.IconGroupDetailResponses;
import com.timeToast.timeToast.global.response.Response;

public interface IconService {
    IconGroupDetailResponses getToastIconGroupsByUser(final long memberId);
    IconGroupDetailResponses getJamIconGroupsByUser(final long memberId);
    IconGroupInfoResponses getAllToastsIconGroups(final long memberId);
    IconGroupInfoResponses getAllJamsIconGroups(final long memberId);
    IconGroupDetail getIconGroupDetail(final long memberId, final long iconGroupId);
    Response deleteIconGroup(final long memberId, final long iconGroupId);
}
