package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventToastRepository {
    List<EventToast> findAllByMemberId(final long memberId);

    EventToast save(final EventToast eventToast);

    EventToast getById(final long eventToastId);

    Optional<EventToast> getByIdAndMemberId(final long eventToastId, final long memberId);

    Optional<EventToast> findByMemberIdAndOpenedDateAndTitle(final long memberId, final LocalDate openedDate, final String title);

    List<EventToast> findAllEventToastToOpen();

    void deleteById(final long eventToastId);
}
