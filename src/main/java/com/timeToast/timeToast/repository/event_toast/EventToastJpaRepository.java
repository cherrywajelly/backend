package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventToastJpaRepository extends JpaRepository<EventToast, Long> {
    List<EventToast> findEventToastsByMemberId(final long memberId);
}
