package com.timeToast.timeToast.service.icon_group;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.icon_group.request.IconGroupPostRequest;

public interface IconGroupService {
    void postIconGroup(IconGroupPostRequest iconGroupPostRequest, long userId);
}
