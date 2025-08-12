package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.member.*;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;
import com.timeToast.timeToast.global.util.DDayCount;
import com.timeToast.timeToast.repository.jpa.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.jpa.follow.FollowRepository;
import com.timeToast.timeToast.repository.jpa.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jpa.jam.JamRepository;
import com.timeToast.timeToast.repository.jpa.member.MemberRepository;
import com.timeToast.timeToast.repository.jpa.showcase.ShowcaseRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.*;
import static com.timeToast.timeToast.global.constant.SuccessConstant.*;


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
    public ResponseWithId saveEventToast(final EventToastPostRequest eventToastPostRequest, final long memberId) {

        if(eventToastPostRequest.title().length() > 20) {
            throw new BadRequestException(INVALID_STRING_FORMAT.getMessage());
        }

        if(eventToastRepository.findByMemberIdAndOpenedDateAndTitle(memberId, eventToastPostRequest.openedDate(), eventToastPostRequest.title()).isPresent()){
            throw new BadRequestException(DUPLICATED_EVENT_TOAST.getMessage());
        }

        EventToast eventToast = eventToastRepository.save(eventToastPostRequest.toEntity(eventToastPostRequest, memberId));

        log.info("save eventToast {} by {}", eventToast.getId(), memberId);
        return new ResponseWithId(eventToast.getId(), StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Transactional(readOnly = true)
    @Override
    public EventToastMyResponses getMyEventToasts(final long memberId) {

        List<EventToastMyResponse> eventToastMyRespons = new ArrayList<>();
        eventToastRepository.findAllByMemberId(memberId).stream().sorted(Comparator.comparing(EventToast::getCreatedAt).reversed()).forEach(
                eventToast -> {
                    Icon icon = iconRepository.getById(eventToast.getIconId());
                    EventToastMyResponse eventToastMyResponse = EventToastMyResponse.fromEntity(eventToast, new IconResponse(icon.getId(), icon.getIconImageUrl()));
                    eventToastMyRespons.add(eventToastMyResponse);
                }
        );
        return new EventToastMyResponses(eventToastMyRespons);
    }

    @Transactional(readOnly = true)
    @Override
    public EventToastResponses getEventToastsFromFollower(final long memberId){
        List<EventToastResponse> eventToastResponses = new ArrayList<>();

        followRepository.findAllByFollowerId(memberId).forEach(
                follow -> {
                    List<EventToast> eventToasts = eventToastRepository.findAllByMemberId(follow.getFollowingId());
                    filterEventToasts(eventToasts, false).forEach(
                            eventToast -> {
                                Member member = memberRepository.getById(eventToast.getMemberId());
                                Icon icon = iconRepository.getById(eventToast.getIconId());
                                boolean isWritten = jamRepository.findByMemberIdAndEventToastId(memberId, eventToast.getId()).isPresent();

                                EventToastResponse eventToastResponse = EventToastResponse.fromEntity(eventToast, member.getNickname(), member.getMemberProfileUrl(),
                                        new IconResponse(icon.getId(), icon.getIconImageUrl()), isWritten, DDayCount.count(LocalDate.now(), eventToast.getOpenedDate()));
                                eventToastResponses.add(eventToastResponse);
                            }
                    );
                }
        );

        eventToastResponses.sort(Comparator.comparingLong(EventToastResponse::dDay));

        return new EventToastResponses(eventToastResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public EventToastResponses getEventToastsOfFollower(final long memberId, final long friendId){
        List<EventToast> eventToasts = eventToastRepository.findAllByMemberId(friendId).stream().sorted(Comparator.comparing(EventToast::getCreatedAt).reversed()).toList();

        List<EventToastResponse> eventToastResponses = new ArrayList<>();

        filterEventToasts(eventToasts, false).forEach(
                eventToast -> {
                    Icon icon = iconRepository.getById(eventToast.getIconId());
                    Member member = memberRepository.getById(friendId);

                    if (jamRepository.findByMemberIdAndEventToastId(memberId, eventToast.getId()).isEmpty()) {
                        EventToastResponse eventToastResponse = EventToastResponse.ofEntity(eventToast, new IconResponse(icon.getId(), icon.getIconImageUrl()), member.getNickname(), member.getMemberProfileUrl(), false);
                        eventToastResponses.add(eventToastResponse);
                    } else {
                        EventToastResponse eventToastResponse = EventToastResponse.ofEntity(eventToast, new IconResponse(icon.getId(), icon.getIconImageUrl()), member.getNickname(), member.getMemberProfileUrl(), true);
                        eventToastResponses.add(eventToastResponse);
                    }
                }
        );

        return new EventToastResponses(eventToastResponses);
    }

    @Transactional(readOnly = true)
    @Override
    public EventToastDetailResponse getEventToastDetail(final long memberId, final long eventToastId) {
        EventToast eventToast = eventToastRepository.getById(eventToastId);
        Icon icon = iconRepository.getById(eventToast.getIconId());
        Member member = memberRepository.getById(eventToast.getMemberId());
        List<Jam> jams = jamRepository.findAllByEventToastId(eventToastId);

        if (eventToast.isOpened()) {
            long dDay = 0;
            List<JamResponse> jamResponses = new ArrayList<>();

            jams.forEach(
                    jam -> {
                        Icon jamIcon = iconRepository.getById(jam.getIconId());
                        Member iconMember = memberRepository.getById(jam.getMemberId());
                        jamResponses.add(JamResponse.from(jam.getId(), jamIcon.getIconImageUrl(), iconMember.getNickname()));
                    }
            );

            EventToastDetailResponse eventToastDetailResponse = EventToastDetailResponse.fromEntity(eventToast, icon.getIconImageUrl(),
                    member.getId(), member.getMemberProfileUrl(), member.getNickname(), jams.size(), dDay, jamResponses);

            return updateWritten(memberId, eventToastId, eventToastDetailResponse);
        }
        else {
            long dDay = ChronoUnit.DAYS.between(LocalDate.now(), eventToast.getOpenedDate());
            EventToastDetailResponse eventToastDetailResponse = EventToastDetailResponse.fromEntity(eventToast, icon.getIconImageUrl(),
                    member.getId(), member.getMemberProfileUrl(), member.getNickname(), jams.size(), dDay, null);
            return updateWritten(memberId, eventToastId, eventToastDetailResponse);
        }

    }

    public EventToastDetailResponse updateWritten(final long memberId, final long eventToastId, EventToastDetailResponse eventToastDetailResponse){

        if (jamRepository.findByMemberIdAndEventToastId(memberId, eventToastId).isEmpty()) {
            return EventToastDetailResponse.of(eventToastDetailResponse, false);
        } else {
            return EventToastDetailResponse.of(eventToastDetailResponse, true);
        }
    }


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
        jamRepository.deleteAllByEventToastId(eventToastId);
        eventToastRepository.deleteById(eventToastId);

        log.info("delete eventToast {} by {}", eventToastId, memberId);
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

                    fcmService.sendMessageTo(eventToast.getMemberId(), new FcmPostRequest(FcmConstant.EVENTTOASTOPENED, null, eventToast.getTitle(), eventToast.getId()));
                });

        log.info("update event toast's is open");
    }
}