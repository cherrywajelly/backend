package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;
import com.timeToast.timeToast.dto.icon.icon_group.response.IconGroupCreatorResponse;

import java.util.List;

public interface IconGroupAdminService {
    void postIconGroup(IconGroupPostRequest iconGroupPostRequest, final long userId);
}
