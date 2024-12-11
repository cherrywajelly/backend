package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.jam.response.JamDetailResponse;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.ResponseWithId;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.jam.JamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.jam.JamService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_JAM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class EventToastIntegrationTest extends TestContainerSupport {

    private final EventToastService eventToastService;
    private final MemberRepository memberRepository;
    private final JamService jamService;
    private final EventToastRepository eventToastRepository;
    private final JamRepository jamRepository;

    @Autowired
    public EventToastIntegrationTest(final EventToastService eventToastService, final MemberRepository memberRepository,
                                     final JamService jamService,
                                     final EventToastRepository eventToastRepository, final JamRepository jamRepository) {
        this.eventToastService = eventToastService;
        this.memberRepository = memberRepository;
        this.jamService = jamService;
        this.eventToastRepository = eventToastRepository;
        this.jamRepository = jamRepository;
    }

    @Test
    @DisplayName("사용자는 이벤트 토스트를 생성하고 조회할 수 있다.")
    public void tryToCreateEventToast (){
        Member member = memberRepository.getById(1L);

        EventToastPostRequest eventToastPostRequest = new EventToastPostRequest(LocalDate.of(2025, 1, 1), "title", 1L, "description");
        ResponseWithId responseWithId = eventToastService.saveEventToast(eventToastPostRequest, 1L);

        EventToastResponse eventToastResponse = eventToastService.getEventToast(member.getId(), responseWithId.id());

        assertThat(eventToastResponse.isOpened()).isFalse();
        assertThat(eventToastResponse.memberId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("사용자는 오픈된 토스트와 토스트에 작성된 잼을 조회할 수 있다.")
    public void tryToGetOpenedEventToast (){
        Member member = memberRepository.getById(1L);

        EventToast eventToast = eventToastRepository.getById(2L);
        assertThat(eventToast.isOpened()).isTrue();

        List<Jam> jams = jamRepository.findAllByEventToastId(eventToast.getId());
        jams.forEach(
                jam -> {
                    JamDetailResponse jamDetailResponse = jamService.getJam(member.getId(), jam.getId());
                    assertThat(jamDetailResponse).isNotNull();
                }
        );
    }

    @Test
    @DisplayName("사용자는 타사용자의 이벤트 토스트 목록을 조회하고 잼을 제외한 이벤트 토스트 내용을 조회할 수 있다.")
    public void tryToGetOtherEventToast (){
        Member member1 = memberRepository.getById(1L);
        Member member2 = memberRepository.getById(2L);

        EventToast eventToast = eventToastRepository.getById(2L);
        assertThat(eventToast.getMemberId()).isEqualTo(member1.getId());
        assertThat(eventToast.isOpened()).isTrue();

        List<Jam> jams = jamRepository.findAllByEventToastId(eventToast.getId());
        jams.forEach(
                jam -> {
                    BadRequestException exception = assertThrows(BadRequestException.class, () -> jamService.getJam(member2.getId(), jam.getId()));
                    assertThat(exception.getMessage()).isEqualTo(INVALID_JAM.getMessage());
                }
        );
    }
}
