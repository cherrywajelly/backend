package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;

import java.util.List;
import java.util.Optional;

public interface EventToastRepository {

    List<EventToast> findAll();
    EventToast save(final EventToast eventToast);
}
