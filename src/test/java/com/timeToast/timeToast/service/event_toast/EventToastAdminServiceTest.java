package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastInfoManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastManagerResponses;
import com.timeToast.timeToast.dto.jam.response.JamManagerResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventToastAdminServiceTest implements EventToastAdminService{
    @Override
    public EventToastManagerResponses getEventToastsForManager() {
        List<EventToastManagerResponse> eventToastManagerResponses = new ArrayList<>();
        eventToastManagerResponses.add(new EventToastManagerResponse(1L, "iconImageUrl", "title", "nickname", LocalDate.of(20204, 1, 1), true, LocalDate.of(2023, 1, 1)));
        return new EventToastManagerResponses(eventToastManagerResponses);
    }

    @Override
    public EventToastInfoManagerResponse getEventToastInfoForManager(final long eventToastId) {
        List<JamManagerResponse> jamManagerResponses = new ArrayList<>();
        jamManagerResponses.add(new JamManagerResponse(1L, "iconImageUrl", "title", LocalDate.of(2024, 11, 11), "nickname"));
        return new EventToastInfoManagerResponse(1L, "imageUrl", "title", "nickname", LocalDate.of(2024, 11, 11), true, LocalDate.of(2024, 11, 10), jamManagerResponses);
    }

    @Override
    public EventToastRequest editEventToast(final long eventToastId, final EventToastRequest eventToastRequest) {
        return eventToastRequest;
    }
}
