package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.icon.icon.response.IconResponse;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class EventToastServiceImpl implements EventToastService{

    private final EventToastRepository eventToastRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final FollowRepository followRepository;


    @Transactional
    public void postEventToast(EventToastPostRequest eventToastPostRequest, long memberId) {
        eventToastRepository.save(eventToastPostRequest.toEntity(eventToastPostRequest, memberId));
        log.info("save event toast");

    }

    // opened_date 가 지난 이벤트 토스트 검증
    @Transactional
    public List<EventToast> checkEventToastOpened(List<EventToast> eventToasts){
        List<EventToast> openedEventToasts = new ArrayList<>();

        List<EventToast> unOpenedEventToasts = eventToasts.stream()
                .filter(eventToast -> !eventToast.isOpened()) // isOpened가 false인 객체만 필터링
                .collect(Collectors.toList());

        unOpenedEventToasts.forEach(
                eventToast -> {
                    // opened_date 지난 게시물 열림 처리
                    if (eventToast.getOpenedDate().isBefore(LocalDate.now())) {
                        eventToast.updateIsOpened(true);
                        openedEventToasts.add(eventToast);
                    }
                }
        );

        if(!openedEventToasts.isEmpty()) {
            eventToastRepository.saveAll(openedEventToasts);
        }

        return eventToasts;
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


    @Transactional(readOnly = true)
    public List<EventToastResponse> getEventToastList(long memberId){
        List<EventToastResponse> eventToastResponseList = new ArrayList<>();

        List<Follow> follows = followRepository.findAllByFollowerId(memberId);

        follows.forEach(
                follow -> {
                    // 팔로우하고 있는 사용자의 이벤트 토스트 조회
//                    List<EventToast> eventToasts = checkEventToastOpened()
                    List<EventToast> eventToasts = eventToastRepository.findByMemberId(follow.getFollowingId());

                    filterEventToasts(checkEventToastOpened(eventToasts), false).forEach(
                            eventToast -> {
                                Member member = memberRepository.getById(memberId);
                                Icon icon = iconRepository.getById(eventToast.getIconId());

                                EventToastResponse eventToastResponse = EventToastResponse.fromEntity(eventToast, member.getNickname(),
                                        new IconResponse(icon.getId(), icon.getIcon_image_url()));
                                eventToastResponseList.add(eventToastResponse);
                            }
                    );
                }
        );

        return eventToastResponseList;
    }

//    public List<EventToastResponse> getMyEventToastList(long memberId) {
//        Member member = memberRepository.getById(memberId);
//
//        List<EventToast> eventToasts = eventToastRepository.findByMemberId(memberId);
//        eventToasts.forEach(
//                eventToast -> {
//
//                }
//        );
//    }
}

