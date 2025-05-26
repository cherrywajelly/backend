package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface EventToastJpaRepository extends JpaRepository<EventToast, Long> {
    List<EventToast> findAllByMemberId(final long memberId);
    Optional<EventToast> findByIdAndMemberId(final long eventToastId, final long memberId);
    Optional<EventToast> findEventToastByMemberIdAndOpenedDateAndTitle(final long memberId, final LocalDate openedDate, final String title);
}
