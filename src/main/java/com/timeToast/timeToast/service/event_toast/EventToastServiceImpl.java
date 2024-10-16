package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.Icon;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.domain.member_icon.MemberIcon;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.icon.response.IconResponse;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.IconRepository;
import com.timeToast.timeToast.repository.icon_group.IconGroupRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EventToastServiceImpl implements EventToastService{

    private final EventToastRepository eventToastRepository;
    private final MemberRepository memberRepository;
    private final IconRepository iconRepository;
    private final IconGroupRepository iconGroupRepository;

    public void postEventToast(EventToastPostRequest eventToastPostRequest, long memberId) {
        Member member = memberRepository.getById(memberId);

        Icon icon = iconRepository.getById(eventToastPostRequest.icon_id());
        eventToastRepository.save(eventToastPostRequest.toEntity(eventToastPostRequest, member, icon));
        System.out.println("이벤트 토스트 등록");

    }

    public List<EventToastResponse> getEventToastList(long memberId){
        Member member = memberRepository.getById(memberId);

        //TODO 팔로우 연동 후 팔로워만 필터링해서 매핑되도록 로직 변경
        List<EventToast> eventToasts = eventToastRepository.findAll();
        List<EventToastResponse> eventToastResponseList = new ArrayList<>();

        for (EventToast eventToast : eventToasts) {
            EventToastResponse eventToastResponse = EventToastResponse.fromEntity(eventToast, eventToast.getMember(),
                    new IconResponse(eventToast.getIcon().getId(), eventToast.getIcon().getIcon_image_url()));
            eventToastResponseList.add(eventToastResponse);
        }

        return eventToastResponseList;
    }
}

