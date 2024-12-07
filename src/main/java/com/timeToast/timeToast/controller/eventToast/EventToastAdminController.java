package com.timeToast.timeToast.controller.eventToast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastInfoManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastManagerResponses;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/eventToasts")
@RestController
@RequiredArgsConstructor
public class EventToastAdminController {
    private final EventToastService eventToastService;

    @GetMapping("")
    public EventToastManagerResponses getEventToastsManager() {
        return eventToastService.getEventToastsForManager();
    }

    @GetMapping("/{eventToastId}")
    public EventToastInfoManagerResponse getEventToastInfoManager(@PathVariable final long eventToastId) {
        return eventToastService.getEventToastInfoForManager(eventToastId);
    }

    @PutMapping("/{eventToastId}")
    public EventToastRequest editEventToast(@PathVariable final long eventToastId, @RequestBody EventToastRequest eventToastRequest) {
        return eventToastService.editEventToast(eventToastId, eventToastRequest);
    }
}
