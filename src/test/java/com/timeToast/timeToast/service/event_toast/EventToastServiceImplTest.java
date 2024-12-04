package com.timeToast.timeToast.service.event_toast;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.follow.Follow;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.event_toast.request.EventToastPostRequest;
import com.timeToast.timeToast.dto.event_toast.response.*;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.NotFoundException;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.global.response.ResponseWithId;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.follow.FollowRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jam.JamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.showcase.ShowcaseRepository;
import com.timeToast.timeToast.service.fcm.FcmServiceImpl;
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
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.ExceptionConstant.EVENT_TOAST_NOT_FOUND;
import static com.timeToast.timeToast.global.constant.SuccessConstant.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventToastServiceImplTest {

    @Mock
    private EventToastRepository eventToastRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private FollowRepository followRepository;

    @Mock
    private JamRepository jamRepository;

    @Mock
    private ShowcaseRepository showcaseRepository;

    @InjectMocks
    private EventToastServiceImpl eventToastService;

    @InjectMocks
    private FcmServiceImpl fcmService;

    private EventToast eventToast;
    private Icon icon;
    private Follow follow;
    private Member member;
    private Jam jam;

    @BeforeEach
    void setUp() {
        long memberId = 1L;
        long followingId = 2L;
        long iconId = 1L;
        String title = "title";
        String imageUrl = "imageUrl";
        String nickname = "nickname";
        LocalDate openedDate = LocalDate.of(2024, 1, 1);

        eventToast = EventToast.builder().memberId(memberId).openedDate(openedDate).title(title).iconId(iconId).memberId(memberId).build();
        icon = Icon.builder().iconImageUrl(imageUrl).build();
        follow = Follow.builder().followerId(memberId).followingId(followingId).build();
        member = Member.builder().nickname(nickname).memberProfileUrl(imageUrl).build();
        jam = Jam.builder().iconId(iconId).memberId(memberId).build();
    }

    @Test
    @DisplayName("이벤트 토스트 저장 성공")
    void saveEventToastSuccess() {
        long memberId = 1L;
        EventToastPostRequest eventToastPostRequest = new EventToastPostRequest(LocalDate.of(2024, 1, 1),"title", 1L,"description");

        when(eventToastRepository.save(any(EventToast.class))).thenReturn(eventToast);

        ResponseWithId response = eventToastService.saveEventToast(eventToastPostRequest, memberId);

        verify(eventToastRepository, times(1)).save(any(EventToast.class));
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }

    @Test
    @DisplayName("마이페이지 이벤트 토스트 목록 조회 성공")
    void getOwnEventToastListSuccess() {
        long memberId = 1L;

        ReflectionTestUtils.setField(eventToast, "id", 1L);
        ReflectionTestUtils.setField(icon, "id", 1L);

        when(eventToastRepository.findAllByMemberId(memberId)).thenReturn(List.of(eventToast));
        when(iconRepository.getById(eventToast.getIconId())).thenReturn(icon);

        EventToastOwnResponses eventToastOwnResponses = eventToastService.getOwnEventToastList(memberId);

        assertThat(eventToastOwnResponses).isNotNull();
    }

    @Test
    @DisplayName("팔로우하고 있는 사용자의 이벤트 토스트 목록 조회 성공 - 잼 미작성")
    void getEventToasts() {
        long memberId = 1L;
        List<Follow> follows = List.of(follow);

        ReflectionTestUtils.setField(eventToast, "id", 1L);
        ReflectionTestUtils.setField(icon, "id", 1L);

        when(followRepository.findAllByFollowerId(memberId)).thenReturn(follows);
        when(eventToastRepository.findAllByMemberId(follow.getFollowingId())).thenReturn(List.of(eventToast));
        when(memberRepository.getById(eventToast.getMemberId())).thenReturn(member);
        when(iconRepository.getById(eventToast.getIconId())).thenReturn(icon);
        when(jamRepository.findByMemberIdAndEventToastId(memberId, eventToast.getId())).thenReturn(Optional.empty());

        EventToastFriendResponses eventToastFriendResponses = eventToastService.getEventToasts(memberId);

        assertThat(eventToastFriendResponses).isNotNull();
    }

    @Test
    @DisplayName("타사용자의 이벤트 토스트 목록 조회 성공 - 잼 미작성 목록 반환")
    void getMemberEventToastsWithUnwrittenJam() {
        long memberId = 1L;
        long friendId = 2L;
        Member friend = Member.builder().nickname("nickname").memberProfileUrl("imageUrl").build();

        ReflectionTestUtils.setField(friend, "id", 2L);
        ReflectionTestUtils.setField(eventToast, "id", 2L);
        ReflectionTestUtils.setField(icon, "id", 1L);

        when(eventToastRepository.findAllByMemberId(friendId)).thenReturn(List.of(eventToast));
        when(iconRepository.getById(eventToast.getIconId())).thenReturn(icon);
        when(memberRepository.getById(friendId)).thenReturn(friend);
        when(jamRepository.findByMemberIdAndEventToastId(memberId, eventToast.getId())).thenReturn(Optional.empty());

        EventToastMemberResponses eventToastMemberResponses = eventToastService.getMemberEventToastList(memberId, friendId);

        assertThat(eventToastMemberResponses).isNotNull();
    }

    @Test
    @DisplayName("타사용자의 이벤트 토스트 목록 조회 성공 - 잼 작성 목록 반환")
    void getMemberEventToastsWithWrittenJam() {
        long memberId = 1L;
        long friendId = 2L;
        Member friend = Member.builder().nickname("nickname").memberProfileUrl("imageUrl").build();

        ReflectionTestUtils.setField(friend, "id", 2L);
        ReflectionTestUtils.setField(eventToast, "id", 2L);
        ReflectionTestUtils.setField(icon, "id", 1L);

        when(eventToastRepository.findAllByMemberId(friendId)).thenReturn(List.of(eventToast));
        when(iconRepository.getById(eventToast.getIconId())).thenReturn(icon);
        when(memberRepository.getById(friendId)).thenReturn(friend);
        when(jamRepository.findByMemberIdAndEventToastId(memberId, eventToast.getId())).thenReturn(Optional.of(jam));

        EventToastMemberResponses eventToastMemberResponses = eventToastService.getMemberEventToastList(memberId, friendId);

        assertThat(eventToastMemberResponses).isNotNull();
    }

    @Test
    @DisplayName("이벤트 토스트 상세 조회 성공")
    void getEventToast() {
        long memberId = 1L;
        long eventToastId = 1L;
        long jamId = 1L;

        ReflectionTestUtils.setField(jam, "id", jamId);
        ReflectionTestUtils.setField(member, "id", memberId);
        ReflectionTestUtils.setField(eventToast, "id", eventToastId);

        when(eventToastRepository.getById(eventToastId)).thenReturn(eventToast);
        when(iconRepository.getById(eventToast.getIconId())).thenReturn(icon);
        when(memberRepository.getById(eventToast.getMemberId())).thenReturn(member);
        when(jamRepository.findAllByEventToastId(eventToastId)).thenReturn(List.of(jam));
        when(iconRepository.getById(jam.getMemberId())).thenReturn(icon);
        when(memberRepository.getById(jam.getMemberId())).thenReturn(member);

        EventToastResponse eventToastResponse = eventToastService.getEventToast(memberId, eventToastId);

        assertThat(eventToastResponse).isNotNull();
    }

    @Test
    @DisplayName("잼 작성 여부 수정 성공")
    void updateWritten() {
        long memberId = 1L;
        long eventToastId = 1L;
        List<JamResponse> jams = List.of(new JamResponse(1L, icon.getIconImageUrl(), member.getNickname()));
        EventToastResponse eventToastResponse = new EventToastResponse(eventToastId, eventToast.getTitle(), eventToast.getOpenedDate(), eventToast.isOpened(),
                icon.getIconImageUrl(), eventToast.getMemberId(), member.getMemberProfileUrl(), member.getNickname(), 0, 0, false, "description", jams);

        when(jamRepository.findByMemberIdAndEventToastId(memberId, eventToastId)).thenReturn(Optional.empty());

        EventToastResponse updatedEventToastResponse = eventToastService.updateWritten(memberId, eventToastId, eventToastResponse);

        assertThat(updatedEventToastResponse.isWritten()).isFalse();
    }


    @Test
    @DisplayName("열람 가능한 이벤트 토스트 목록 반환 성공")
    void filterOpenedEventToasts() {
        List<EventToast> eventToasts = List.of(eventToast);
        ReflectionTestUtils.setField(eventToast, "isOpened", true);

        List<EventToast> updatedEventToasts = eventToastService.filterEventToasts(eventToasts, true);

        assertThat(updatedEventToasts.get(0).isOpened()).isTrue();
    }

    @Test
    @DisplayName("열람 불가능한 이벤트 토스트 목록 반환 성공")
    void filterUnopenedEventToasts() {
        List<EventToast> eventToasts = List.of(eventToast);
        ReflectionTestUtils.setField(eventToast, "isOpened", false);

        List<EventToast> updatedEventToasts = eventToastService.filterEventToasts(eventToasts, false);

        assertThat(updatedEventToasts.get(0).isOpened()).isFalse();
    }

    @Test
    @DisplayName("이벤트 토스트 삭제 성공")
    void deleteEventToastSuccess() {
        long memberId = 1L;
        long eventToastId = 1L;
        ReflectionTestUtils.setField(eventToast, "id", eventToastId);

        when(eventToastRepository.getByIdAndMemberId(eventToastId, memberId)).thenReturn(Optional.of(eventToast));
        doNothing().when(showcaseRepository).deleteAllByEventToastId(eventToastId);
        doNothing().when(jamRepository).deleteAllByEventToastId(eventToastId);
        doNothing().when(eventToastRepository).deleteById(eventToastId);

        Response response = eventToastService.deleteEventToast(memberId, eventToastId);

        verify(showcaseRepository, times(1)).deleteAllByEventToastId(eventToastId);
        verify(jamRepository, times(1)).deleteAllByEventToastId(eventToastId);
        verify(eventToastRepository, times(1)).deleteById(eventToastId);
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_DELETE.getMessage());
    }

    @Test
    @DisplayName("이벤트 토스트 삭제 실패 - 이벤트 토스트가 존재하지 않는 경우 예외 발생")
    void deleteEventToastFail() {
        long memberId = 1L;
        long eventToastId = 1L;
        ReflectionTestUtils.setField(eventToast, "id", eventToastId);

        when(eventToastRepository.getByIdAndMemberId(eventToastId, memberId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> eventToastService.deleteEventToast(memberId, eventToastId));

        assertThat(exception.getMessage()).isEqualTo(EVENT_TOAST_NOT_FOUND.getMessage());
        verify(eventToastRepository, times(1)).getByIdAndMemberId(eventToastId, memberId);
    }

    @Test
    @DisplayName("이벤트 토스트 전체 삭제 성공")
    void deleteAllEventToastsByMemberId() {
        long memberId = 1L;
        long eventToastId = 1L;
        ReflectionTestUtils.setField(eventToast, "id", eventToastId);

        when(eventToastRepository.findAllByMemberId(memberId)).thenReturn(List.of(eventToast));
        doNothing().when(jamRepository).deleteAllByEventToastId(eventToast.getId());
        doNothing().when(eventToastRepository).deleteById(eventToast.getId());

        eventToastService.deleteAllEventToastByMemberId(memberId);

        verify(jamRepository, times(1)).deleteAllByEventToastId(eventToast.getId());
        verify(eventToastRepository, times(1)).deleteById(eventToast.getId());
    }

    @Test
    @DisplayName("관리자 이벤트 토스트 목록 조회 성공 ")
    void getEventToastsManager() {
        long iconId = 1L;
        long memberId = 1L;
        long eventToastId = 1L;
        ReflectionTestUtils.setField(eventToast, "id", eventToastId);

        when(eventToastRepository.findAll()).thenReturn(List.of(eventToast));
        when(iconRepository.getById(iconId)).thenReturn(icon);
        when(memberRepository.getById(memberId)).thenReturn(member);

        EventToastManagerResponses eventToastManagerResponses = eventToastService.getEventToastsForManager();

        assertThat(eventToastManagerResponses).isNotNull();
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

        EventToastInfoManagerResponse eventToastInfoManagerResponse = eventToastService.getEventToastInfoForManager(memberId);

        assertThat(eventToastInfoManagerResponse).isNotNull();
    }
}