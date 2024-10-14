package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import org.springframework.stereotype.Repository;

@Repository
public class EventToastRepositoryImpl implements EventToastRepository{
    private EventToastJpaRepository eventToastJpaRepository;

    public EventToastRepositoryImpl(final EventToastJpaRepository eventToastJpaRepository){
        this.eventToastJpaRepository = eventToastJpaRepository;
    }

    @Override
    public EventToast save(final EventToast eventToast) {
        return eventToastJpaRepository.save(eventToast);
    }
}
