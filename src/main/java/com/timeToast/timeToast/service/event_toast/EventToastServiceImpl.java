package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.event_toast.EventToast;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventToastServiceImpl implements EventToastService{

    private final EventToastRepository eventToastRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final FollowRepository followRepository;


    public void postEventToast(EventToastPostRequest eventToastPostRequest, long memberId) {
        Member member = memberRepository.getById(memberId);

        Icon icon = iconRepository.getById(eventToastPostRequest.icon_id());
        eventToastRepository.save(eventToastPostRequest.toEntity(eventToastPostRequest, member, icon));
        System.out.println("이벤트 토스트 등록");

    }

    // opened_date 가 지난 이벤트 토스트만 검증해서 반환
    public List<EventToast> checkEventToastOpened(List<EventToast> eventToasts){
        List<EventToast> openedEventToasts = new ArrayList<>();

        eventToasts.forEach(
                eventToast -> {
                    if (eventToast.getOpenedDate().isBefore(LocalDate.now())){
                        eventToast.updateIsOpened(true);
                        eventToastRepository.save(eventToast);
                        openedEventToasts.add(eventToast);
                    }
                }
        );

        return openedEventToasts;
    }


    public List<EventToastResponse> getEventToastList(long memberId){
        List<EventToastResponse> eventToastResponseList = new ArrayList<>();

        List<Follow> follows = followRepository.findAllByFollowerId(memberId);

        follows.forEach(
                follow -> {
                    // 팔로우하고 있는 사용자의 이벤트 토스트 조회
                    List<EventToast> eventToasts = eventToastRepository.findEventToastsByMemberId(follow.getFollowingId());

                    checkEventToastOpened(eventToasts).forEach(
                            eventToast -> {
                                EventToastResponse eventToastResponse = EventToastResponse.fromEntity(eventToast, eventToast.getMember(),
                                        new IconResponse(eventToast.getIcon().getId(), eventToast.getIcon().getIcon_image_url()));
                                eventToastResponseList.add(eventToastResponse);
                            }
                    );
                }
        );

        return eventToastResponseList;
    }
}

