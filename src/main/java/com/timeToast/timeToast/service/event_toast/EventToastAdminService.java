package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastInfoManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastManagerResponses;

public interface EventToastAdminService {
    EventToastManagerResponses getEventToastsForManager();
    EventToastInfoManagerResponse getEventToastInfoForManager(final long eventToastId);
    EventToastRequest editEventToast(final long eventToastId, final EventToastRequest eventToastRequest);
}
