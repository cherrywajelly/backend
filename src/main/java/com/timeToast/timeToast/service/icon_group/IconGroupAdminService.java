package com.timeToast.timeToast.service.icon_group;

import com.timeToast.timeToast.dto.icon_group.request.IconGroupPostRequest;

public interface IconGroupAdminService {
    void postIconGroup(IconGroupPostRequest iconGroupPostRequest, long userId);
}
