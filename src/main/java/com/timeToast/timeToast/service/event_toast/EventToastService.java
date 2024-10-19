package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;

import java.util.List;

public interface EventToastService {
    void postEventToast(EventToastPostRequest eventToastPostRequest, long userId);

    List<EventToastResponse> getEventToastList(long userId);
}
