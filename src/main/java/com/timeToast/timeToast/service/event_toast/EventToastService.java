package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;

public interface EventToastService {
    void postEventToast(EventToastPostRequest eventToastPostRequest, long userId);
}
