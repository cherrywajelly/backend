package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.creator.response.CreatorIconInfos;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupStateRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupInfoResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupInfoResponses;
import com.timeToast.timeToast.global.response.Response;

import java.util.List;

public interface IconGroupAdminService {
    Response postIconGroup(IconGroupPostRequest iconGroupPostRequest, final long userId);
    IconGroupCreatorResponses getIconGroupForCreator(final long memberId);
    IconGroupDetailResponse getIconGroupDetail(final long iconGroupId);
    IconGroupInfoResponse saveIconState(final IconGroupStateRequest iconGroupStateRequest);
    IconGroupInfoResponses getIconGroupForNonApproval();
    CreatorIconInfos getIconGroupsByCreator(final long creatorId);
    IconGroupInfoResponses getAllIconGroups();
    IconGroupCreatorDetailResponse getIconGroupDetailForCreator(final long memberId, final long iconGroupId);
}
