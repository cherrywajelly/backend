package com.timeToast.timeToast.repository.event_toast;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.timeToast.timeToast.domain.event_toast.QEventToast.eventToast;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.EVENT_TOAST_NOT_FOUND;

@Repository
@RequiredArgsConstructor
public class EventToastRepositoryImpl implements EventToastRepository{
    private final EventToastJpaRepository eventToastJpaRepository;
    private final JPAQueryFactory queryFactory;

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
    public List<EventToast> findAllByMemberId(final long memberId) {
        return eventToastJpaRepository.findByAllMemberId(memberId);
    }


    @Override
    public EventToast getByIdAndMemberId(final long eventToastId, final long memberId) {
        return eventToastJpaRepository.findByIdAndMemberId(eventToastId, memberId);
    }

    @Override
    public List<EventToast> findAllEventToastToOpen() {
        return queryFactory
                .selectFrom(eventToast)
                .where(eventToast.isOpened.isFalse(),
                        eventToast.openedDate.before(LocalDate.now()).or(eventToast.openedDate.eq(LocalDate.now())))
                .fetch();
    }

    @Override
    public void deleteById(final long eventToastId) {
        eventToastJpaRepository.deleteById(eventToastId);
    }
}
