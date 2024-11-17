package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastFriendResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastOwnResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponses;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jam.JamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;


@Service
@Slf4j
@RequiredArgsConstructor
public class EventToastServiceImpl implements EventToastService{

    private final EventToastRepository eventToastRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final FollowRepository followRepository;
    private final JamRepository jamRepository;
    private final ShowcaseRepository showcaseRepository;
    private final FcmService fcmService;


    @Transactional
    @Override
    public Response postEventToast(final EventToastPostRequest eventToastPostRequest, final long memberId) {
        memberRepository.getById(memberId);
        eventToastRepository.save(eventToastPostRequest.toEntity(eventToastPostRequest, memberId));
        log.info("save event toast");
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventToastOwnResponse> getOwnEventToastList(final long memberId) {

        List<EventToastOwnResponse> eventToastOwnResponses = new ArrayList<>();
        eventToastRepository.findAllByMemberId(memberId).forEach(
                eventToast -> {
                    Icon icon = iconRepository.getById(eventToast.getIconId());
                    EventToastOwnResponse eventToastOwnResponse = EventToastOwnResponse.fromEntity(eventToast, new IconResponse(icon.getId(), icon.getIconImageUrl()));
                    eventToastOwnResponses.add(eventToastOwnResponse);
                }
        );
        return eventToastOwnResponses;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventToastResponses> getEventToasts(final long memberId){
        List<EventToastResponses> eventToastResponses = new ArrayList<>();

        followRepository.findAllByFollowerId(memberId).forEach(
                follow -> {
                    // 팔로우하고 있는 사용자의 이벤트 토스트 조회
                    List<EventToast> eventToasts = eventToastRepository.findAllByMemberId(follow.getFollowingId());
                    filterEventToasts(eventToasts, false).forEach(
                            eventToast -> {
                                Member member = memberRepository.getById(eventToast.getMemberId());
                                Icon icon = iconRepository.getById(eventToast.getIconId());
                                if (jamRepository.findByMemberIdAndEventToastId(memberId, eventToast.getId()).isEmpty()) {
                                    EventToastResponses eventToastResponse = EventToastResponses.fromEntity(eventToast, member.getNickname(), member.getMemberProfileUrl(),
                                            new IconResponse(icon.getId(), icon.getIconImageUrl()), false);
                                    eventToastResponses.add(eventToastResponse);
                                } else {
                                    EventToastResponses eventToastResponse = EventToastResponses.fromEntity(eventToast, member.getNickname(), member.getMemberProfileUrl(),
                                            new IconResponse(icon.getId(), icon.getIconImageUrl()), true);
                                    eventToastResponses.add(eventToastResponse);
                                }
                            }
                    );
                }
        );

        return eventToastResponses;
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventToastFriendResponse> getFriendEventToastList(final long memberId, final long friendId){
        List<EventToast> eventToasts = eventToastRepository.findAllByMemberId(friendId);
        List<EventToastFriendResponse> eventToastFriendResponses = new ArrayList<>();

        filterEventToasts(eventToasts, false).forEach(
                eventToast -> {
                    Icon icon = iconRepository.getById(eventToast.getIconId());
                    Member member = memberRepository.getById(friendId);

                    // 작성한 잼이 없는 경우
                    if (jamRepository.findByMemberIdAndEventToastId(memberId, eventToast.getId()).isEmpty()) {
                        EventToastFriendResponse eventToastFriendResponse = EventToastFriendResponse.fromEntity(eventToast, new IconResponse(icon.getId(), icon.getIconImageUrl()), member.getNickname(), member.getMemberProfileUrl(), false);
                        eventToastFriendResponses.add(eventToastFriendResponse);
                    } else {
                        EventToastFriendResponse eventToastFriendResponse = EventToastFriendResponse.fromEntity(eventToast, new IconResponse(icon.getId(), icon.getIconImageUrl()), member.getNickname(), member.getMemberProfileUrl(), true);
                        eventToastFriendResponses.add(eventToastFriendResponse);
                    }
                }
        );

        return eventToastFriendResponses;
    }

    @Transactional(readOnly = true)
    @Override
    public EventToastResponse getEventToast(final long memberId, final long eventToastId) {
        EventToast eventToast = eventToastRepository.getById(eventToastId);
        Icon icon = iconRepository.getById(eventToast.getIconId()); //이벤트 토스트 아이콘
        Member member = memberRepository.getById(eventToast.getMemberId()); // 이벤트 토스트 주인
        List<Jam> jams = jamRepository.findAllByEventToastId(eventToastId); // 이벤트 토스트 잼 조회

//         이벤트 토스트가 열려있을 경우
        if (eventToast.isOpened()) {
            long dDay = 0;
            List<JamResponses> jamResponses = new ArrayList<>();

            jams.forEach(
                    jam -> {
                        Icon jamIcon = iconRepository.getById(jam.getIconId()); // 잼 아이콘
                        Member iconMember = memberRepository.getById(jam.getMemberId()); // 잼 작성자
                        jamResponses.add(JamResponses.from(jam.getId(), jamIcon.getIconImageUrl(), iconMember.getNickname()));
                    }
            );

//            Jam memberJam = jamRepository.findByMemberIdAndEventToastId(memberId, eventToastId).get();
            EventToastResponse eventToastResponse = EventToastResponse.fromEntity(eventToast, icon.getIconImageUrl(),  member.getId() ,member.getMemberProfileUrl(), member.getNickname(),
                    jams.size(), dDay, jamResponses);

            return updateWritten(memberId, eventToastId, eventToastResponse);
        }
        else {
            long dDay = ChronoUnit.DAYS.between(LocalDate.now(), eventToast.getOpenedDate());
            EventToastResponse eventToastResponse = EventToastResponse.fromEntity(eventToast, icon.getIconImageUrl(), member.getId(), member.getMemberProfileUrl(), member.getNickname(),
                    jams.size(), dDay, null);
            return updateWritten(memberId, eventToastId, eventToastResponse);
        }

    }

    public EventToastResponse updateWritten(final long memberId, final long eventToastId, EventToastResponse eventToastRes){

        if (jamRepository.findByMemberIdAndEventToastId(memberId, eventToastId).isEmpty()) {
            return EventToastResponse.of(eventToastRes, false);
        } else {
            return EventToastResponse.of(eventToastRes, true);
        }
    }


    // isOpened ? 열린 토스트 리스트 반환 : 닫힌 토스트 리스트 반환
    @Transactional(readOnly = true)
    public List<EventToast> filterEventToasts(List<EventToast> eventToasts, boolean isOpened) {
        List<EventToast> openedEventToasts = new ArrayList<>();
        List<EventToast> unOpenedEventToasts = new ArrayList<>();

        eventToasts.forEach(
                eventToast -> {
                    if (eventToast.isOpened()) { openedEventToasts.add(eventToast); }
                    else { unOpenedEventToasts.add(eventToast); }
                }
        );

        return isOpened ? openedEventToasts : unOpenedEventToasts;
    }


    @Transactional
    @Override
    public Response deleteEventToast(final long memberId,final long eventToastId) {
        if(eventToastRepository.getByIdAndMemberId(eventToastId, memberId).isEmpty()) {
            throw new NotFoundException(EVENT_TOAST_NOT_FOUND.getMessage());
        }

        showcaseRepository.deleteAllByEventToastId(eventToastId);
        eventToastRepository.deleteById(eventToastId);
        log.info("delete event toast");
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());

    }


    @Transactional
    @Override
    public void deleteAllEventToastByMemberId(final long memberId){
        eventToastRepository.findAllByMemberId(memberId).forEach(
                eventToast -> {
                    jamRepository.deleteAllByEventToastId(eventToast.getId());
                    eventToastRepository.deleteById(eventToast.getId());
                }
        );
    }


    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateIsOpen(){
        List<EventToast> eventToasts = eventToastRepository.findAllEventToastToOpen();

        eventToasts.forEach(
                eventToast -> {
                    eventToast.updateIsOpened(true);

                    fcmService.sendMessageTo(eventToast.getMemberId(), new FcmResponse(FcmConstant.EVENTTOASTOPENED, null, eventToast.getTitle(), eventToast.getId()));
                });

        log.info("update event toast's is open");
    }
}

