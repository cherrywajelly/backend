package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastFriendResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponses;
import java.util.List;

public class EventToastServiceTest implements EventToastService {

    @Override
    public void postEventToast(EventToastPostRequest eventToastPostRequest, final long memberId) {

    }

    @Override
    public List<EventToastOwnResponse> getOwnEventToastList(final long memberId) {
        return null;
    }

    @Override
    public List<EventToastResponses> getEventToasts(final long memberId){

        return null;
    }

    @Override
    public List<EventToastFriendResponse> getFriendEventToastList(final long memberId, final long friendId){

        return null;
    }

    @Override
    public EventToastResponse getEventToast(final long memberId, final long eventToastId) {
        return null;
    }

    @Override
    public void deleteEventToast(final long memberId,final long eventToastId) {

    }
}
