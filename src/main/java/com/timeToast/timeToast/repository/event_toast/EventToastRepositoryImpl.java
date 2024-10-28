package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventToastRepositoryImpl implements EventToastRepository{
    private final EventToastJpaRepository eventToastJpaRepository;

    @Override
    public List<EventToast> findByMemberId(final long memberId) {
        return eventToastJpaRepository.findByMemberId(memberId);
    }

    @Override
    public EventToast findByEventToastId(final long eventToastId) {
        return eventToastJpaRepository.findByEventToastId(eventToastId);
    }


    @Override
    public EventToast save(final EventToast eventToast) {
        return eventToastJpaRepository.save(eventToast);
    }

    @Override
    public List<EventToast> saveAll(List<EventToast> eventToasts) { return eventToastJpaRepository.saveAll(eventToasts); }
}
