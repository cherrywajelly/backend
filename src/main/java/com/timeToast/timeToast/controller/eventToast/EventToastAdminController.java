package com.timeToast.timeToast.controller.eventToast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastInfoManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastManagerResponses;
import com.timeToast.timeToast.service.event_toast.EventToastAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/eventToasts")
@RestController
@RequiredArgsConstructor
public class EventToastAdminController {
    private final EventToastAdminService eventToastAdminService;

    @GetMapping("")
    public EventToastManagerResponses getEventToastsManager() {
        return eventToastAdminService.getEventToastsForManager();
    }

    @GetMapping("/{eventToastId}")
    public EventToastInfoManagerResponse getEventToastInfoManager(@PathVariable final long eventToastId) {
        return eventToastAdminService.getEventToastInfoForManager(eventToastId);
    }

    @PutMapping("/{eventToastId}")
    public EventToastRequest editEventToast(@PathVariable final long eventToastId, @RequestBody EventToastRequest eventToastRequest) {
        return eventToastAdminService.editEventToast(eventToastId, eventToastRequest);
    }
}
