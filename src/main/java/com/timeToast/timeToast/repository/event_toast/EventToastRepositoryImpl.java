package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.global.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_FOUND;

@Repository
public class EventToastRepositoryImpl implements EventToastRepository{
    private EventToastJpaRepository eventToastJpaRepository;

    public EventToastRepositoryImpl(final EventToastJpaRepository eventToastJpaRepository){
        this.eventToastJpaRepository = eventToastJpaRepository;
    }

    //TODO 사용자가 팔로우 하는 대상만 조회
    @Override
    public List<EventToast> findAll() {
        return eventToastJpaRepository.findAll();
    }

    @Override
    public EventToast save(final EventToast eventToast) {
        return eventToastJpaRepository.save(eventToast);
    }
}
