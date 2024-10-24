package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.event_toast.EventToast;

import java.util.List;

public interface EventToastRepository {
    List<EventToast> findEventToastsByMemberId(final long memberId);
    EventToast save(final EventToast eventToast);
}
