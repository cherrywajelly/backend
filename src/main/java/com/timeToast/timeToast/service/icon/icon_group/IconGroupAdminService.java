package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorDetail;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponse;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponses;
import com.timeToast.timeToast.global.response.Response;

import java.util.List;

public interface IconGroupAdminService {
    Response postIconGroup(IconGroupPostRequest iconGroupPostRequest, final long userId);
    IconGroupCreatorResponses getIconGroupForCreator(final long memberId);
    IconGroupCreatorDetail getIconGroupDetailForCreator(final long memberId, final long iconGroupId);
}
