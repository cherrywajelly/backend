package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.*;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;

public interface EventToastService {
    ResponseWithId saveEventToast(EventToastPostRequest eventToastPostRequest, final long memberId);
    EventToastOwnResponses getOwnEventToastList(final long memberId);
    EventToastMemberResponses getMemberEventToastList(final long memberId, final long friendId);
    EventToastFriendResponses getEventToasts(final long memberId);
    EventToastResponse getEventToast(final long memberId, final long eventToastId);
    void deleteAllEventToastByMemberId(final long memberId);
    Response deleteEventToast(final long memberId, final long eventToastId);
    EventToastManagerResponses getEventToastsForManager();
    EventToastInfoManagerResponse getEventToastInfoForManager(final long eventToastId);
}
