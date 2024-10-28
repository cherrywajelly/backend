package com.timeToast.timeToast.service.icon.icon_group;

import com.timeToast.timeToast.dto.icon.icon_group.request.IconGroupPostRequest;

public interface IconGroupAdminService {
    void postIconGroup(IconGroupPostRequest iconGroupPostRequest, long userId);
}
