package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.dto.event_toast.response.member.*;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
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
    public EventToastMyResponses getMyEventToasts(final long memberId) {
        List<EventToastMyResponse> eventToastMyResponses = new ArrayList<>();
        eventToastMyResponses.add(new EventToastMyResponse(1, "title", LocalDate.of(2024, 11, 11), new IconResponse(1, "iconUrl")));
        return new EventToastMyResponses(eventToastMyResponses);
    }

    @Override
    public EventToastResponses getEventToastsOfFollower(final long memberId, final long friendId){
        List<EventToastResponse> eventToastResponses = new ArrayList<>();
        eventToastResponses.add(new EventToastResponse(1, "title", LocalDate.of(2024, 11, 11), false, "nickname", "imageUrl", new IconResponse(1, "iconUrl"), 0));
        return new EventToastResponses(eventToastResponses);
    }


    @Override
    public EventToastResponses getEventToastsFromFollower(final long memberId){
        List<EventToastResponse> eventToastResponses = new ArrayList<>();
        eventToastResponses.add(new EventToastResponse(1, "title", LocalDate.of(2024, 11, 11), false, "nickname", "profileImageUrl", new IconResponse(1, "iconUrl"), 5));
        return new EventToastResponses(eventToastResponses);
    }

    @Override
    public EventToastDetailResponse getEventToastDetail(final long memberId, final long eventToastId) {
        List<JamResponse> jamResponses = new ArrayList<>();
        jamResponses.add(new JamResponse(1, "title", "iconUrl"));
        EventToastDetailResponse eventToastDetailResponse = new EventToastDetailResponse(1, "title", LocalDate.of(2024, 11, 11),
                false, "iconUrl", 1, "profileUrl", "nickname", 0, 1, false, "description",jamResponses);
        return eventToastDetailResponse;
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
}
