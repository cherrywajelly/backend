package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.member.EventToastResponses;
import com.timeToast.timeToast.dto.event_toast.response.member.EventToastMyResponses;
import com.timeToast.timeToast.dto.event_toast.response.member.EventToastDetailResponse;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;

public interface EventToastService {
    ResponseWithId saveEventToast(EventToastPostRequest eventToastPostRequest, final long memberId);
    EventToastMyResponses getMyEventToastList(final long memberId);
    EventToastResponses getEventToastsOfFollower(final long memberId, final long friendId);
    EventToastResponses getEventToastsFromFollower(final long memberId);
    EventToastDetailResponse getEventToast(final long memberId, final long eventToastId);
    void deleteAllEventToastByMemberId(final long memberId);
    Response deleteEventToast(final long memberId, final long eventToastId);
}
