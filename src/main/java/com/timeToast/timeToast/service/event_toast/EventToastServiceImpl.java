package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.Icon;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventToastServiceImpl implements EventToastService{

    private final EventToastRepository eventToastRepository;
    private final MemberRepository memberRepository;

    public void postEventToast(EventToastPostRequest eventToastPostRequest, long userId) {
        Member member = memberRepository.getById(userId);

        if (eventToastPostRequest.title().isBlank()) {
            // 에러 메세지 반환
        } else {
            EventToast eventToast = eventToastRepository.save(eventToastPostRequest.toEntity(eventToastPostRequest, member));
            // 성공 response 반환
        }

    }

    public List<EventToastResponse> getEventToastList(long userId){
        Member member = memberRepository.getById(userId);


        //TODO 팔로우 연동 후 팔로워만 필터링해서 매핑되도록 로직 변경
        //미완성 코드
//        List<EventToast> eventToastResponses = eventToastRepository.findAll();
//
//        List<EventToastResponse> eventToastList = eventToastResponses.stream()
//                .map(eventToast -> new EventToast(
//                        eventToast.getId(),
//                        eventToast.getTitle(),
//                        eventToast.getOpened_date(),
//                        eventToast.getMember().getNickname(),
//                        new Icon(eventToast.getIcon().getId(), eventToast.getIcon().getIcon_image_url())
//                ))
//                .collect(Collectors.toList());

    }
}

