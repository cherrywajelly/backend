package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.*;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.EVENT_TOAST_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class EventToastServiceTest implements EventToastService {

    @Override
    public ResponseWithId saveEventToast(EventToastPostRequest eventToastPostRequest, final long memberId) {
        return new ResponseWithId(1L, StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public EventToastOwnResponses getOwnEventToastList(final long memberId) {
        List<EventToastOwnResponse> eventToastOwnResponses = new ArrayList<>();
        eventToastOwnResponses.add(new EventToastOwnResponse(1, "title", LocalDate.of(2024, 11, 11), new IconResponse(1, "iconUrl")));
        return new EventToastOwnResponses(eventToastOwnResponses);
    }

    @Override
    public EventToastMemberResponses getMemberEventToastList(final long memberId, final long friendId){
        List<EventToastMemberResponse> eventToastMemberResponses = new ArrayList<>();
        eventToastMemberResponses.add(new EventToastMemberResponse(1, "title", LocalDate.of(2024, 11, 11), false, "nickname", "imageUrl", new IconResponse(1, "iconUrl")));
        return new EventToastMemberResponses(eventToastMemberResponses);
    }


    @Override
    public EventToastFriendResponses getEventToasts(final long memberId){
        List<EventToastFriendResponse> eventToastResponses = new ArrayList<>();
        eventToastResponses.add(new EventToastFriendResponse(1, "title", LocalDate.of(2024, 11, 11), "nickname", "profileImageUrl", new IconResponse(1, "iconUrl"), false, 5));
        return new EventToastFriendResponses(eventToastResponses);
    }

    @Override
    public EventToastResponse getEventToast(final long memberId, final long eventToastId) {
        List<JamResponse> jamResponses = new ArrayList<>();
        jamResponses.add(new JamResponse(1, "title", "iconUrl"));
        EventToastResponse eventToastResponse = new EventToastResponse(1, "title", LocalDate.of(2024, 11, 11),
                false, "iconUrl", 1, "profileUrl", "nickname", 0, 1, false, "description",jamResponses);
        return eventToastResponse;
    }

    @Override
    public void deleteAllEventToastByMemberId(long memberId) {

    }

    @Override
    public Response deleteEventToast(final long memberId,final long eventToastId) {
        if(eventToastId == 2){
            throw new NotFoundException(EVENT_TOAST_NOT_FOUND.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }

    @Override
    public EventToastManagerResponses getEventToastsForManager() {
        List<EventToastManagerResponse> eventToastManagerResponses = new ArrayList<>();
        return new EventToastManagerResponses(eventToastManagerResponses);
    }
}
