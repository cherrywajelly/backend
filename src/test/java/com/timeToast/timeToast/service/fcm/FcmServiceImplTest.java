package com.timeToast.timeToast.service.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.domain.fcm.Fcm;
import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.fcm.requset.FcmSendRequest;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.config.FirebaseConfig;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.fcm.FcmRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_PUT;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@TestPropertySource(properties = "fcm.path=classpath:test-firebase-config.json")
@ExtendWith(MockitoExtension.class)
public class FcmServiceImplTest {

    @Mock
    private MemberTokenRepository memberTokenRepository;

    @Mock
    private FcmRepository fcmRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private EventToastRepository eventToastRepository;

    @Mock
    private IconRepository iconRepository;

    @Mock
    private FirebaseMessaging firebaseMessaging;

    @Mock
    private Message message;

//    @Mock
//    private FirebaseApp firebaseApp;

    @InjectMocks
    private FcmServiceImpl fcmService;

    private MemberToken memberToken;
    private Fcm fcm;
    private Member member;

    private FirebaseApp firebaseApp;
    private GoogleCredentials googleCredentials;
    private EventToast eventToast;
    private Icon icon;

    @BeforeEach
    public void setUp() {
        long memberId = 1L;
        long iconId = 1L;

        memberToken = MemberToken.builder().memberId(memberId).jwt_refresh_token("jwtToken").build();
        fcm = Fcm.builder().fcmConstant(FcmConstant.EVENTTOASTOPENED).param(1L).build();
        member = Member.builder().memberProfileUrl("profileUrl").build();
        eventToast = EventToast.builder().iconId(iconId).build();
        icon = Icon.builder().iconImageUrl("imageUrl").build();

//        try {
//            Message message = mock(Message.class);
//            when(FirebaseMessaging.getInstance().send(message)).thenReturn(null);
//        } catch (FirebaseMessagingException e) {
//            throw new RuntimeException(e);
//        }

//        firebaseApp = mock(FirebaseApp.class);
//        googleCredentials = mock(GoogleCredentials.class);

        // createScoped() 메서드를 mock하여 GoogleCredentials을 반환하도록 설정
//        when(googleCredentials.createScoped(anyList())).thenReturn(googleCredentials);
//
//        // FirebaseOptions을 mock 설정
//        FirebaseOptions options = mock(FirebaseOptions.class);
////        when(options.getCredential()).thenReturn(googleCredentials);
//
//        // FirebaseApp이 제대로 초기화되는지 확인
//        when(firebaseApp.getOptions()).thenReturn(options);
//
//        // FirebaseMessaging 객체의 동작을 mock
//        FirebaseMessaging firebaseMessaging = mock(FirebaseMessaging.class);
//        try {
//            when(firebaseMessaging.send(any())).thenReturn("message-id");
//        } catch (FirebaseMessagingException e) {
//            e.printStackTrace();
//        }
//
//
//        // FirebaseApp.initializeApp() 메서드를 mock하여 위에서 설정한 FirebaseApp을 반환
//        FirebaseApp.initializeApp(options);

    }

    @BeforeEach
    void resetFirebaseApp() throws IOException {

        for (FirebaseApp app : FirebaseApp.getApps()) {
            app.delete();
        }

        FileInputStream json = mock(FileInputStream.class);
        String mockUrl = "mockUrl";
        GoogleCredentials googleCredentials = mock(GoogleCredentials.class);

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(googleCredentials)
                .setDatabaseUrl("https://mock-database-url.firebaseio.com") // Mock URL
                .setProjectId("timetoast-1abe6")
                .build();

        FirebaseApp.initializeApp(options);
    }

    @Test
    @DisplayName("fcm 토큰 저장 - 성공")
    void saveToken() {
        // Given
        long memberId = 1L;
        String token = "token";

        when(memberTokenRepository.findByMemberId(memberId)).thenReturn(Optional.of(memberToken));
        when(memberTokenRepository.save(any(MemberToken.class))).thenReturn(memberToken);

        // When
        Response response = fcmService.saveToken(memberId, token);

        // Then
        assertThat(memberToken.getFcmToken()).isEqualTo(token);
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }


    @Test
    @DisplayName("기존 fcm 토큰 검증 - 성공")
    void fcmTokenValidationNewToken() {
        // Given
        long memberId = 2L;
        String token = "token";
        memberToken.updateFcmToken(token);

        when(memberTokenRepository.findByFcmToken(token)).thenReturn(Optional.of(memberToken));
        when(memberTokenRepository.save(any(MemberToken.class))).thenReturn(memberToken);

        // When
        fcmService.fcmTokenValidation(memberId, token);

        //then
        assertNull(memberToken.getFcmToken());
    }

    @Test
    @DisplayName("새 fcm 토큰 검증 - 성공")
    void fcmTokenValidationExistingToken() {
        // Given
        long memberId = 1L;
        String token = "token";

        when(memberTokenRepository.findByFcmToken(token)).thenReturn(Optional.of(memberToken));

        // When
        fcmService.fcmTokenValidation(memberId, token);

        // Then
        assertNull(memberToken.getFcmToken());
    }

    @Test
    @DisplayName("fcm 목록 조회 - 성공")
    void getFcmResponses() {
        // Given
        long memberId = 1L;
        ReflectionTestUtils.setField(fcm, "createdAt", LocalDateTime.of(2024, 1, 1, 1, 1));

        when(fcmRepository.findByMemberIdOrderByCreatedAtDesc(memberId)).thenReturn(List.of(fcm));

        // When
        FcmResponses fcmResponses = fcmService.getFcmResponses(memberId);

        // Then
        assertThat(fcmResponses).isNotNull();
    }

    @Test
    @DisplayName("fcm 조회 여부 변경 - 성공")
    void putIsOpened() {
        // Given
        long memberId = 1L;
        long fcmId = 1L;

        ReflectionTestUtils.setField(fcm, "id", 1L);

        when(fcmRepository.getById(fcmId)).thenReturn(fcm);
        when(fcmRepository.save(any(Fcm.class))).thenReturn(fcm);

        // When
        Response response = fcmService.putIsOpened(memberId, fcmId);

        // Then
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_PUT.getMessage());
    }

//    @Test
//    @DisplayName("fcm 메세지 전송 - 성공")
//    void sendMessageTo() {
//        // Given
//        long memberId = 1L;
//        long eventToastId = 1L;
//        long iconId = 1L;
//        String fcmToken = "fcmToken";
//        ReflectionTestUtils.setField(memberToken, "fcmToken", fcmToken);
//        ReflectionTestUtils.setField(eventToast, "id", eventToastId);
//        FcmPostRequest fcmPostRequest = FcmPostRequest.builder().fcmConstant(FcmConstant.EVENTTOASTOPENED).nickname("nickname").toastName("toastName").param(1L).build();
//        FcmSendRequest fcmSendRequest = FcmSendRequest.builder().build();
//        // When
//
//        FirebaseApp firebaseApp = mock(FirebaseApp.class);
//
//        try {
//            when(eventToastRepository.getById(eventToastId)).thenReturn(eventToast);
//            when(iconRepository.getById(iconId)).thenReturn(icon);
////            when(fcmRepository.save(any(Fcm.class))).thenReturn(fcm);
//            when(fcmService.createMessage(memberId, fcmPostRequest)).thenReturn(null);
////            when(fcmRepository.save(any(Fcm.class))).thenReturn(fcm);
////            when(fcmService.saveFcmInfo(memberId, fcmPostRequest)).thenReturn(mock(Response.class));
////            when(fcmRepository.save(any(Fcm.class))).thenReturn(fcm);
//            when(fcmService.makeMessage(memberId, fcmPostRequest)).thenReturn(Optional.of(fcmSendRequest));
//            when(memberTokenRepository.findByMemberId(memberId)).thenReturn(Optional.of(memberToken));
////            try {
////                when(FirebaseMessaging.getInstance().send(message)).thenReturn("result");
////            } catch (FirebaseMessagingException e) {
////                e.getMessage();
////            }
////            when(fcmService.saveFcmInfo(memberId, fcmPostRequest)).thenReturn(mock(Response.class));
////            when(fcmRepository.save(any(Fcm.class))).thenReturn(fcm);
//
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException(e);
//        }

//        when(FirebaseApp.getApps()).thenReturn("[DEFAULT]");

//        Response response = fcmService.sendMessageTo(memberId, fcmPostRequest);
//        RuntimeException exception = assertThrows(RuntimeException.class , () -> fcmService.sendMessageTo(memberId, fcmPostRequest));
//        // Then
////        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
////        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
//    }

    @Test
    @DisplayName("fcm 정보 저장 - 성공")
    void saveFcmInfo() {
        // Given
        long memberId = 1L;
        FcmPostRequest fcmPostRequest = FcmPostRequest.builder().fcmConstant(FcmConstant.FOLLOW).param(1L).build();

        when(fcmRepository.save(any(Fcm.class))).thenReturn(fcm);
        when(memberRepository.getById(memberId)).thenReturn(member);
        // When
        Response response = fcmService.saveFcmInfo(memberId, fcmPostRequest);

        // Then
        assertThat(response.statusCode()).isEqualTo(StatusCode.OK.getStatusCode());
        assertThat(response.message()).isEqualTo(SUCCESS_POST.getMessage());
    }

    @Test
    @DisplayName("fcm 메세지 생성 - 성공")
    void createMessage() throws JsonProcessingException {
        // Given
        long memberId = 1L;
        FcmPostRequest fcmPostRequest = FcmPostRequest.builder().nickname("nickname").param(1L).fcmConstant(FcmConstant.FOLLOW).build();

        ReflectionTestUtils.setField(memberToken, "fcmToken", "fcm token");

        when(memberTokenRepository.findByMemberId(memberId)).thenReturn(Optional.of(memberToken));

        // When
        Message message = fcmService.createMessage(memberId, fcmPostRequest);

        // Then
        assertThat(message).isNotNull();
    }

    @Test
    @DisplayName("fcm 메세지 글 생성 - 성공")
    void makeMessage() throws JsonProcessingException {
        // Given
        long memberId = 1L;
        FcmPostRequest fcmPostRequest = FcmPostRequest.builder().nickname("nickname").param(1L).fcmConstant(FcmConstant.FOLLOW).build();
        ReflectionTestUtils.setField(memberToken, "fcmToken", "fcm token");

        when(memberTokenRepository.findByMemberId(memberId)).thenReturn(Optional.of(memberToken));

        // When
        Optional<FcmSendRequest> fcmSendRequest = fcmService.makeMessage(memberId, fcmPostRequest);

        // Then
        assertThat(fcmSendRequest).isNotNull();
    }
}