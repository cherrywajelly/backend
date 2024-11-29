package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupInfoResponses;
import com.timeToast.timeToast.global.response.Response;

public interface IconGroupAdminService {
    Response postIconGroup(IconGroupPostRequest iconGroupPostRequest, final long userId);
    IconGroupCreatorResponses getIconGroupForCreator(final long memberId);
    IconGroupDetailResponse getIconGroupDetail(final long iconGroupId);
    IconGroupInfoResponse saveIconState(final IconGroupStateRequest iconGroupStateRequest);
    IconGroupInfoResponses getIconGroupForNonApproval();
    IconGroupInfoResponses getAllIconGroups();
}
