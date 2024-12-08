package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.jam.response.JamDetailResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.response.ResponseWithId;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.event_toast.EventToastServiceImpl;
import com.timeToast.timeToast.service.jam.JamService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class EventToastIntegrationTest extends TestContainerSupport {

    private final EventToastService eventToastService;
    private final EventToastServiceImpl eventToastServiceImpl;
    private final MemberRepository memberRepository;
    private final JamService jamService;

    @Autowired
    public EventToastIntegrationTest(final EventToastService eventToastService, final MemberRepository memberRepository,
                                     final JamService jamService, final EventToastServiceImpl eventToastServiceImpl) {
        this.eventToastService = eventToastService;
        this.memberRepository = memberRepository;
        this.jamService = jamService;
        this.eventToastServiceImpl = eventToastServiceImpl;
    }

    @Test
    @DisplayName("사용자는 이벤트 토스트를 생성하고 조회할 수 있다.")
    public void tryToCreateEventToast (){
        Member member = memberRepository.getById(1L);

        // 이벤트 토스트 생성
        EventToastPostRequest eventToastPostRequest = new EventToastPostRequest(LocalDate.of(2025, 1, 1), "title", 1L, "description");
        ResponseWithId responseWithId = eventToastService.saveEventToast(eventToastPostRequest, 1L);

        // 기한이 지나지 않아 열리지 않은 이벤트 토스트
        EventToastResponse eventToastResponse = eventToastService.getEventToast(member.getId(), responseWithId.id());
        assertThat(eventToastResponse.isOpened()).isFalse();

        assertThat(responseWithId.id()).isEqualTo(2L);
    }

    @Test
    @DisplayName("사용자는 오픈된 토스트와 토스트에 작성된 잼을 조회할 수 있다.")
    public void tryToGetOpenedEventToast (){
        Member member = memberRepository.getById(1L);
        // 열린 이벤트 토스트 생성
        EventToastPostRequest eventToastPostRequest = new EventToastPostRequest(LocalDate.of(2024, 1, 1), "title", 1L, "description");
        ResponseWithId eventToast = eventToastService.saveEventToast(eventToastPostRequest, member.getId());
        eventToastServiceImpl.updateIsOpen();

        // 오픈되어 조회 가능한 이벤트 토스트 조회
        EventToastResponse eventToastResponse = eventToastService.getEventToast(member.getId(), 1L);
        assertThat(eventToastResponse.dDay()).isEqualTo(0L);
        assertThat(eventToastResponse.isOpened()).isTrue();

        // 이벤트 토스트 내 잼 조회
        JamResponses jamResponses = jamService.getJams(eventToastResponse.eventToastId());
        jamResponses.jamResponses().forEach(
                jamResponse -> {
                    JamDetailResponse jamDetailResponse = jamService.getJam(member.getId(), jamResponse.jamId());
                    assertThat(jamDetailResponse).isNotNull();
                }
        );
    }

    @Test
    @DisplayName("사용자는 타사용자의 이벤트 토스트 목록을 조회하고 잼을 제외한 이벤트 토스트 내용을 조회할 수 있다.")
    public void tryToGetOtherEventToast (){
        Member member1 = memberRepository.getById(1L);
        Member member2 = memberRepository.getById(2L);

        // 오픈된 이벤트 토스트 조회
        eventToastService.getEventToasts(2L);
        EventToastResponse eventToastResponse = eventToastService.getEventToast(member1.getId(), 1L);
        assertThat(eventToastResponse.isOpened()).isTrue();

        // 이벤트 토스트 타사용자 잼 조회 불가
        JamResponses jamResponses = jamService.getJams(eventToastResponse.eventToastId());
        jamResponses.jamResponses().forEach(
                jamResponse -> {
                    JamDetailResponse jamDetailResponse = jamService.getJam(member2.getId(), jamResponse.jamId());
                    assertThat(jamDetailResponse).isNull();
                }
        );
    }
}
