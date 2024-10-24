package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.event_toast.EventToast;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventToastRepositoryImpl implements EventToastRepository{
    private final EventToastJpaRepository eventToastJpaRepository;

    @Override
    public List<EventToast> findEventToastsByMemberId(final long memberId) {
        return eventToastJpaRepository.findEventToastsByMemberId(memberId);
    }


    @Override
    public EventToast save(final EventToast eventToast) {
        return eventToastJpaRepository.save(eventToast);
    }
}
