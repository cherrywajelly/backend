package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.dto.icon.icon.request.IconPostRequest;

import java.util.List;

public interface IconService {
    // TODO multipartFile로 변경
    void postIconSet(List<IconPostRequest> images, long iconGroupId);
}
