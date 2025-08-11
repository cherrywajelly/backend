package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastDetailResponse;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastResponses;

public interface AdminEventToastService {
    ManagerEventToastResponses getEventToastsForManager();
    ManagerEventToastDetailResponse getEventToastInfoForManager(final long eventToastId);
    EventToastRequest editEventToast(final long eventToastId, final EventToastRequest eventToastRequest);
}
