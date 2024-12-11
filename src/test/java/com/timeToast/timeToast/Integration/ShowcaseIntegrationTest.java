package com.timeToast.timeToast.Integration;

import com.timeToast.timeToast.TimeToastApplication;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.response.EventToastResponse;
import com.timeToast.timeToast.dto.showcase.request.ShowcaseSaveRequest;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseEditResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseResponses;
import com.timeToast.timeToast.dto.showcase.response.ShowcaseSaveResponses;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.event_toast.EventToastService;
import com.timeToast.timeToast.service.showcase.ShowcaseService;
import com.timeToast.timeToast.util.TestContainerSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimeToastApplication.class)
@ActiveProfiles("test")
public class ShowcaseIntegrationTest extends TestContainerSupport {

    private final ShowcaseService showcaseService;
    private final MemberRepository memberRepository;
    private final EventToastService eventToastService;

    @Autowired
    public ShowcaseIntegrationTest(final ShowcaseService showcaseService, final MemberRepository memberRepository, final EventToastService eventToastService) {
        this.showcaseService = showcaseService;
        this.memberRepository = memberRepository;
        this.eventToastService = eventToastService;
    }
    @Test
    @DisplayName("사용자는 본인의 진열장 목록을 통해 이벤트 토스트를 상세조회 할 수 있습니다.")
    public void tryToGetEventToastByShowcase() {
        Member member = memberRepository.getById(1L);

        ShowcaseResponses showcaseResponses = showcaseService.getShowcase(member.getId());
        EventToastResponse eventToastResponse = eventToastService.getEventToast(member.getId(), showcaseResponses.showcaseResponses().get(0).eventToastId());
        assertThat(eventToastResponse.memberId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("사용자는 진열장 목록 편집을 통해 원하는 이벤트 토스트를 진열할 수 있습니다.")
    public void tryToSelectEventToastByShowcase() {
        Member member = memberRepository.getById(1L);

        ShowcaseEditResponses showcaseEditResponses = showcaseService.getShowcaseSaveList(member.getId());

        List<Long> showcases = new ArrayList<>();
        showcases.add(showcaseEditResponses.showcaseEditResponses().get(0).eventToastId());

        ShowcaseSaveRequest showcaseSaveRequest = new ShowcaseSaveRequest(showcases);
        ShowcaseSaveResponses showcaseSaveResponses = showcaseService.saveShowcase(member.getId(), showcaseSaveRequest);
        assertThat(showcaseSaveResponses.showcaseSaveResponses().get(0)).isEqualTo(showcaseEditResponses.showcaseEditResponses().get(0).title());
    }
}
