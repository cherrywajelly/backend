package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastDetailResponse;
import com.timeToast.timeToast.dto.event_toast.response.manager.ManagerEventToastResponses;
import com.timeToast.timeToast.repository.jpa.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.jpa.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jpa.jam.JamRepository;
import com.timeToast.timeToast.repository.jpa.member.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminEventToastServiceImplTest {
    @Mock
    private EventToastRepository eventToastRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private JamRepository jamRepository;

    @InjectMocks
    private AdminEventToastServiceImpl adminEventToastService;

    private EventToast eventToast;
    private Member member;
    private Icon icon;
    private Jam jam;

    @BeforeEach
    void setUp() {
        long memberId = 1L;
        long iconId = 1L;
        String title = "title";
        String imageUrl = "imageUrl";
        String nickname = "nickname";
        LocalDate openedDate = LocalDate.of(2024, 1, 1);

        eventToast = EventToast.builder().memberId(memberId).openedDate(openedDate).title(title).iconId(iconId).memberId(memberId).build();
        member = Member.builder().nickname(nickname).memberProfileUrl(imageUrl).build();
        icon = Icon.builder().iconImageUrl(imageUrl).build();
        jam = Jam.builder().iconId(iconId).memberId(memberId).build();
    }

    @Test
    @DisplayName("관리자 이벤트 토스트 목록 조회 성공 ")
    void getEventToastsManager() {
        long iconId = 1L;
        long memberId = 1L;
        long eventToastId = 1L;
        ReflectionTestUtils.setField(eventToast, "id", eventToastId);
        ReflectionTestUtils.setField(eventToast, "createdAt", LocalDateTime.of(2024, 1, 1, 0, 0));
        ReflectionTestUtils.setField(eventToast, "isOpened", true);

        when(eventToastRepository.findAll()).thenReturn(List.of(eventToast));
        when(iconRepository.getById(iconId)).thenReturn(icon);
        when(memberRepository.getById(memberId)).thenReturn(member);

        ManagerEventToastResponses managerEventToastResponses = adminEventToastService.getEventToastsForManager();

        assertThat(managerEventToastResponses).isNotNull();
    }

    @Test
    @DisplayName("관리자 이벤트 토스트 상세 조회 성공 ")
    void getEventToastInfoForManager() {
        long eventToastId = 1L;
        long memberId = 1L;
        long iconId = 1L;
        ReflectionTestUtils.setField(jam, "createdAt", LocalDateTime.of(2024,1,1,0,0));
        ReflectionTestUtils.setField(eventToast, "id", eventToastId);
        ReflectionTestUtils.setField(eventToast, "createdAt", LocalDateTime.of(2024,1,1,0,0));

        when(eventToastRepository.getById(eventToastId)).thenReturn(eventToast);
        when(memberRepository.getById(memberId)).thenReturn(member);
        when(iconRepository.getById(iconId)).thenReturn(icon);
        when(jamRepository.findAllByEventToastId(eventToastId)).thenReturn(List.of(jam));
        when(iconRepository.getById(iconId)).thenReturn(icon);
        when(memberRepository.getById(memberId)).thenReturn(member);

        ManagerEventToastDetailResponse managerEventToastDetailResponse = adminEventToastService.getEventToastInfoForManager(memberId);

        assertThat(managerEventToastDetailResponse).isNotNull();
    }
}
