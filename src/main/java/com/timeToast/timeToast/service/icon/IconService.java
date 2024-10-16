package com.timeToast.timeToast.service.icon;

import com.timeToast.timeToast.dto.icon.request.IconPostRequest;
import com.timeToast.timeToast.dto.icon.response.IconDto;

import java.util.Set;

public interface IconService {
    void postIconSet(Set<IconPostRequest> iconPostRequestSet, long memberId);
}
