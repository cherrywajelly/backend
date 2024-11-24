package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorDetailResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.global.response.Response;

public interface IconGroupAdminService {
    Response postIconGroup(IconGroupPostRequest iconGroupPostRequest, final long userId);
    IconGroupCreatorResponses getIconGroupForCreator(final long memberId);
    IconGroupCreatorDetailResponse getIconGroupDetailForCreator(final long memberId, final long iconGroupId);
}
