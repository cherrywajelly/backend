package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.icon.response.IconDto;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventToastServiceImpl implements EventToastService{

    private final EventToastRepository eventToastRepository;
    private final MemberRepository memberRepository;

    public void postEventToast(EventToastPostRequest eventToastPostRequest, long memberId) {
        Member member = memberRepository.getById(memberId);

        if (eventToastPostRequest.title().isBlank()) {
            // 에러 메세지 반환
        } else {
            EventToast eventToast = eventToastRepository.save(eventToastPostRequest.toEntity(eventToastPostRequest, member));
            // 성공 response 반환
        }

    }

    public List<EventToastResponse> getEventToastList(long memberId){
        Member member = memberRepository.getById(memberId);

        //TODO 팔로우 연동 후 팔로워만 필터링해서 매핑되도록 로직 변경
        List<EventToast> eventToasts = eventToastRepository.findAll();
        List<EventToastResponse> eventToastResponseList = new ArrayList<>();

        for (EventToast eventToast : eventToasts) {
            EventToastResponse eventToastResponse = EventToastResponse.fromEntity(eventToast, eventToast.getMember(),
                    new IconDto(eventToast.getIcon().getId(), eventToast.getIcon().getIcon_image_url()));
            eventToastResponseList.add(eventToastResponse);
        }

        return eventToastResponseList;
    }
}

