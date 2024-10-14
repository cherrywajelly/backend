package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.member.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        }
        else {
            EventToast eventToast = eventToastRepository.save(eventToastPostRequest.toEntity(eventToastPostRequest, member));
            // 성공 response 반환
            System.out.println("성공티비~");
        }

    }
}
