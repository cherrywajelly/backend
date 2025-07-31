package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastDetailResponse;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastResponse;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastResponses;
import com.timeToast.timeToast.dto.jam.response.JamManagerResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventToastAdminServiceTest implements EventToastAdminService{
    @Override
    public ManagerEventToastResponses getEventToastsForManager() {
        List<ManagerEventToastResponse> managerEventToastResponses = new ArrayList<>();
        managerEventToastResponses.add(new ManagerEventToastResponse(1L, "iconImageUrl", "title", "nickname", LocalDate.of(20204, 1, 1), true, LocalDate.of(2023, 1, 1)));
        return new ManagerEventToastResponses(managerEventToastResponses);
    }

    @Override
    public ManagerEventToastDetailResponse getEventToastInfoForManager(final long eventToastId) {
        List<JamManagerResponse> jamManagerResponses = new ArrayList<>();
        jamManagerResponses.add(new JamManagerResponse(1L, "iconImageUrl", "title", LocalDate.of(2024, 11, 11), "nickname"));
        return new ManagerEventToastDetailResponse(1L, "imageUrl", "title", "nickname", LocalDate.of(2024, 11, 11), true, LocalDate.of(2024, 11, 10), jamManagerResponses);
    }

    @Override
    public EventToastRequest editEventToast(final long eventToastId, final EventToastRequest eventToastRequest) {
        return eventToastRequest;
    }
}
