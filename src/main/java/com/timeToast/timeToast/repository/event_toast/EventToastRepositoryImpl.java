package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.MEMBER_NOT_FOUND;

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
