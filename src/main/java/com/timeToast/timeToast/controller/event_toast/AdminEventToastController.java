package com.timeToast.timeToast.controller.event_toast;

import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastDetailResponse;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastResponses;
import com.timeToast.timeToast.service.event_toast.AdminEventToastService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v3/eventToasts")
@RestController
@RequiredArgsConstructor
public class AdminEventToastController {
    private final AdminEventToastService adminEventToastService;

    @GetMapping("")
    public ManagerEventToastResponses getEventToastsManager() {
        return adminEventToastService.getEventToastsForManager();
    }

    @GetMapping("/{eventToastId}")
    public ManagerEventToastDetailResponse getEventToastInfoManager(@PathVariable final long eventToastId) {
        return adminEventToastService.getEventToastInfoForManager(eventToastId);
    }

    @PutMapping("/{eventToastId}")
    public EventToastRequest editEventToast(@PathVariable final long eventToastId, @RequestBody EventToastRequest eventToastRequest) {
        return adminEventToastService.editEventToast(eventToastId, eventToastRequest);
    }
}
