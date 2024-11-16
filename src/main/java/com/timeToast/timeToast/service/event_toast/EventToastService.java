package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastFriendResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponses;
import com.timeToast.timeToast.global.response.Response;

import java.util.List;

public interface EventToastService {
    Response postEventToast(EventToastPostRequest eventToastPostRequest, final long memberId);
    List<EventToastOwnResponse> getOwnEventToastList(final long memberId);
    List<EventToastFriendResponse> getFriendEventToastList(final long memberId, final long friendId);
    List<EventToastResponses> getEventToasts(final long memberId);
    EventToastResponse getEventToast(final long memberId, final long eventToastId);
    void deleteAllEventToastByMemberId(final long memberId);
    Response deleteEventToast(final long memberId, final long eventToastId);
}
