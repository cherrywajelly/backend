package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;

import java.util.List;

public interface EventToastRepository {
    List<EventToast> findByMemberId(final long memberId);
    EventToast save(final EventToast eventToast);

    List<EventToast> saveAll(List<EventToast> eventToasts);
    EventToast findById(final long eventToastId);
    EventToast findByIdAndMemberId(final long eventToastId, final long memberId);

    void deleteById(final long eventToastId);
}
