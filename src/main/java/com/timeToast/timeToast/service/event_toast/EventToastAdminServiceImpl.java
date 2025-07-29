package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastRequest;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastInfoManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastManagerResponse;
import com.timeToast.timeToast.dto.event_toast.response.admin.EventToastManagerResponses;
import com.timeToast.timeToast.dto.jam.response.JamManagerResponse;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jam.JamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventToastAdminServiceImpl implements EventToastAdminService {

    private final EventToastRepository eventToastRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final JamRepository jamRepository;

    @Transactional(readOnly = true)
    @Override
    public EventToastManagerResponses getEventToastsForManager() {
        List<EventToastManagerResponse> eventToastManagerResponses = new ArrayList<>();
        List<EventToast> eventToasts = eventToastRepository.findAll();

        eventToasts.forEach(
                eventToast -> {
                    Icon icon = iconRepository.getById(eventToast.getIconId());
                    Member member = memberRepository.getById(eventToast.getMemberId());
                    eventToastManagerResponses.add(EventToastManagerResponse.from(eventToast, icon.getIconImageUrl(), member.getNickname()));
                }
        );

        return new EventToastManagerResponses(eventToastManagerResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public EventToastInfoManagerResponse getEventToastInfoForManager(final long eventToastId) {
        EventToast eventToast = eventToastRepository.getById(eventToastId);
        Member member = memberRepository.getById(eventToast.getMemberId());
        Icon icon = iconRepository.getById(eventToast.getIconId());

        List<JamManagerResponse> jamManagerResponses = new ArrayList<>();
        List<Jam> jams = jamRepository.findAllByEventToastId(eventToastId);
        jams.forEach(
                jam -> {
                    Icon jamIcon = iconRepository.getById(jam.getIconId());
                    Member jamMember = memberRepository.getById(jam.getMemberId());
                    jamManagerResponses.add(JamManagerResponse.from(jam, jamIcon.getIconImageUrl(), jamMember.getNickname()));
                }
        );

        return EventToastInfoManagerResponse.from(eventToast, icon.getIconImageUrl(), member.getNickname(), jamManagerResponses);
    }

    @Transactional
    @Override
    public EventToastRequest editEventToast(final long eventToastId, final EventToastRequest eventToastRequest) {
        EventToast eventToast = eventToastRepository.getById(eventToastId);

        eventToast.updateOpenedDateAndIsOpened(eventToastRequest.openedDate(), eventToastRequest.isOpened());
        eventToastRepository.save(eventToast);

        log.info("edit event toast");
        return eventToastRequest;
    }
}
