package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastFriendResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponses;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class EventToastServiceTest implements EventToastService {

    @Override
    public Response postEventToast(EventToastPostRequest eventToastPostRequest, final long memberId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public List<EventToastOwnResponse> getOwnEventToastList(final long memberId) {
        List<EventToastOwnResponse> eventToastOwnResponseList = new ArrayList<>();
        eventToastOwnResponseList.add(new EventToastOwnResponse(1, "title", LocalDate.of(2024, 11, 11), new IconResponse(1, "iconUrl")));
        return eventToastOwnResponseList;
    }

    @Override
    public List<EventToastFriendResponse> getFriendEventToastList(final long memberId, final long friendId){
        List<EventToastFriendResponse> eventToastFriendResponseList = new ArrayList<>();
        eventToastFriendResponseList.add(new EventToastFriendResponse(1, "title", LocalDate.of(2024, 11, 11), false, "nickname", new IconResponse(1, "iconUrl")));
        return eventToastFriendResponseList;
    }


    @Override
    public List<EventToastResponses> getEventToasts(final long memberId){
        List<EventToastResponses> eventToastResponseList = new ArrayList<>();
        eventToastResponseList.add(new EventToastResponses(1, "title", LocalDate.of(2024, 11, 11), "nickname", "profileImageUrl", new IconResponse(1, "iconUrl"), false));
        return eventToastResponseList;
    }

    @Override
    public EventToastResponse getEventToast(final long memberId, final long eventToastId) {
        List<JamResponses> jamResponseList = new ArrayList<>();
        jamResponseList.add(new JamResponses(1, "title", "iconUrl"));
        EventToastResponse eventToastResponse = new EventToastResponse(1, "title", LocalDate.of(2024, 11, 11),
                false, "iconUrl", 1, "profileUrl", "nickname", 0, 1, false, jamResponseList);
        return eventToastResponse;
    }

    @Override
    public void deleteAllEventToastByMemberId(long memberId) {

    }

    @Override
    public Response deleteEventToast(final long memberId,final long eventToastId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}
