package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;

import java.util.List;
import java.util.Optional;

public interface EventToastRepository {
    List<EventToast> findAllByMemberId(final long memberId);

    EventToast save(final EventToast eventToast);

    List<EventToast> saveAll(List<EventToast> eventToasts);

    EventToast getById(final long eventToastId);

    Optional<EventToast> getByIdAndMemberId(final long eventToastId, final long memberId);

    List<EventToast> findAllEventToastToOpen();

    void deleteById(final long eventToastId);
}
