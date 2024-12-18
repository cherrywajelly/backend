package com.timeToast.timeToast.service.jam;

import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.jam.Jam;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.jam.response.JamDetailResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.jam.JamRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.image.FileUploadService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JamServiceImplTest {

    @Mock
    private JamRepository jamRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EventToastRepository eventToastRepository;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private FileUploadService fileUploadService;

    @Mock
    private FcmService fcmService;

    @InjectMocks
    private JamServiceImpl jamService;

    private Member member;
    private EventToast eventToast;
    private Icon icon;
    private Jam jam;

    @BeforeEach
    void setUp() {
        long memberId = 1L;
        long memberId2 = 2L;
        long eventToastId = 1L;
        long iconId = 1L;
        String nickname = "nickname";
        String imageUrl = "imageUrl";

        member = Member.builder().nickname(nickname).build();
        eventToast = EventToast.builder().memberId(memberId).iconId(iconId).build();
        icon = Icon.builder().iconImageUrl(imageUrl).build();
        jam = Jam.builder().memberId(memberId2).eventToastId(eventToastId).iconId(iconId).build();
    }

    @Test
    @DisplayName("잼 생성 - 성공")
    void saveJam () throws IOException {
        // Given
        MultipartFile content = mock(MultipartFile.class);
        MultipartFile image = mock(MultipartFile.class);
        JamRequest jamRequest = new JamRequest("title", 1L);

        ReflectionTestUtils.setField(eventToast, "id", 1L);
        ReflectionTestUtils.setField(member, "id", 1L);

        when(eventToastRepository.getById(eventToast.getId())).thenReturn(eventToast);
        when(jamRepository.findByMemberIdAndEventToastId(member.getId(), eventToast.getId())).thenReturn(Optional.empty());
        when(jamRepository.save(any(Jam.class))).thenReturn(jam);
        when(fileUploadService.uploadfile(any(), any())).thenReturn("file url");
        when(fcmService.sendMessageTo(anyLong(), any(FcmPostRequest.class))).thenReturn(new Response(StatusCode.OK.getStatusCode(), "success"));

        // When
        Response response = jamService.postJam(jamRequest, content, image, eventToast.getId(), member.getId());

        // Then
        verify(jamRepository, times(1)).save(jam);
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }

    @Test
    @DisplayName("잼 목록 조회 - 성공")
    void getJams() {
        // Given
        ReflectionTestUtils.setField(jam, "id", 1L);
        ReflectionTestUtils.setField(eventToast, "id", 1L);
        List<Jam> jams = List.of(jam);


        when(jamRepository.findAllByEventToastId(eventToast.getId())).thenReturn(jams);
        when(iconRepository.getById(jam.getIconId())).thenReturn(icon);
        when(memberRepository.getById(jam.getMemberId())).thenReturn(member);

        // When
        JamResponses jamResponses = jamService.getJams(eventToast.getId());

        // Then
        assertThat(jamResponses).isNotNull();
    }

    @Test
    @DisplayName("잼 상세 조회 - 성공")
    void getJam() {
        // Given
        Member eventToastMember = Member.builder().nickname("nickname").memberProfileUrl("profile").build();
        Member jamMember = Member.builder().nickname("nickname").memberProfileUrl("profile").build();
        ReflectionTestUtils.setField(eventToastMember, "id", eventToast.getMemberId());
        ReflectionTestUtils.setField(jamMember, "id", jam.getMemberId());

        Icon eventToastIcon = Icon.builder().iconImageUrl("image").build();
        Icon jamIcon = Icon.builder().iconImageUrl("image").build();
        ReflectionTestUtils.setField(eventToastIcon, "id", icon.getId());
        ReflectionTestUtils.setField(jamIcon, "id", icon.getId());

        ReflectionTestUtils.setField(jam, "id", 1L);
        ReflectionTestUtils.setField(jam, "createdAt", LocalDateTime.of(2024, 1, 1, 1, 1));
        ReflectionTestUtils.setField(eventToast, "id", 1L);

        when(jamRepository.getById(jam.getId())).thenReturn(jam);
        when(eventToastRepository.getById(jam.getEventToastId())).thenReturn(eventToast);
        when(memberRepository.getById(eventToast.getMemberId())).thenReturn(eventToastMember);
        when(memberRepository.getById(jam.getMemberId())).thenReturn(jamMember);
        when(iconRepository.getById(eventToast.getIconId())).thenReturn(eventToastIcon);
        when(iconRepository.getById(jam.getIconId())).thenReturn(jamIcon);

        // When
        JamDetailResponse jamDetailResponse = jamService.getJam(1L, jam.getId());

        // Then
        assertThat(jamDetailResponse).isNotNull();
    }

    @Test
    @DisplayName("잼 삭제 - 성공")
    void deleteJam() {
        //Given
        ReflectionTestUtils.setField(jam, "id", 1L);

        when(jamRepository.getById(jam.getId())).thenReturn(jam);
        when(eventToastRepository.getById(jam.getEventToastId())).thenReturn(eventToast);
        doNothing().when(jamRepository).deleteById(jam.getId());

        //When
        Response response = jamService.deleteJam(eventToast.getMemberId(), jam.getId());

        //Then
        verify(jamRepository, times(1)).deleteById(jam.getId());
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_DELETE.getMessage());
    }
}
