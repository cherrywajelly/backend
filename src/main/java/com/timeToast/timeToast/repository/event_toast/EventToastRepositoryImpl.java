package com.timeToast.timeToast.repository.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.EVENT_TOAST_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class EventToastRepositoryImpl implements EventToastRepository{
    private final EventToastJpaRepository eventToastJpaRepository;

    @Override
    public EventToast save(final EventToast eventToast) {
        return eventToastJpaRepository.save(eventToast);
    }

    @Override
    public List<EventToast> saveAll(List<EventToast> eventToasts) { return eventToastJpaRepository.saveAll(eventToasts); }

    @Override
    public EventToast getById(final long eventToastId) {
        return eventToastJpaRepository.findById(eventToastId).orElseThrow(() -> new NotFoundException(EVENT_TOAST_NOT_FOUND.getMessage()));
    }

    @Override
    public List<EventToast> findByMemberId(final long memberId) {
        return eventToastJpaRepository.findByMemberId(memberId);
    }

    @Override
    public EventToast findByIdAndMemberId(final long eventToastId, final long memberId) {
        return eventToastJpaRepository.findByIdAndMemberId(eventToastId, memberId);
    }

    @Override
    public void deleteById(final long eventToastId) {
        eventToastJpaRepository.deleteById(eventToastId);
    }
}
