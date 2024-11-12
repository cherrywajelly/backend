package com.timeToast.timeToast.service.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.timeToast.timeToast.domain.fcm.Fcm;
import com.timeToast.timeToast.domain.member.member.Member;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.dto.fcm.requset.*;
import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.fcm.FcmRepository;
import com.timeToast.timeToast.repository.member.member.MemberRepository;
import com.timeToast.timeToast.repository.member.member_token.MemberTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.domain.enums.fcm.FcmConstant.*;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_FCM_TOKEN;

@Service
@Slf4j
@RequiredArgsConstructor
public class FcmServiceImpl implements FcmService {

    @Value("${fcm.url}")
    private String fcmUrl;

    @Value("${fcm.path}")
    private String fcmPath;

    @Value("${fcm.credential}")
    private String fcmCredential;

    private final MemberTokenRepository memberTokenRepository;

    private final FcmRepository fcmRepository;

    @Transactional
    @Override
    public void saveToken(final long memberId, final String token){
        MemberToken memberToken = memberTokenRepository.findByMemberId(memberId).orElseThrow();
        if (token != null) {
            memberToken.updateFcmToken(token);
            memberTokenRepository.save(memberToken);
        } else {
            throw new BadRequestException(INVALID_FCM_TOKEN.getMessage());
        }
    }

    //TODO 1.최신순으로 조회되는지  2.각 경우에 맞게 시간 계산되는지 3.데이터 잘 반환되는지
    @Transactional
    @Override
    public List<FcmResponses> getFcmResponses(final long memberId){
        List<FcmResponses> fcmResponses = new ArrayList<>();
        List<Fcm> fcms = fcmRepository.findByMemberId(memberId);
        fcms.forEach(
                fcm -> {

                    String text = fcm.getFcmConstant().value();
                    LocalDateTime localDateTime = LocalDateTime.now().plusHours(9);
                    Duration duration = Duration.between(fcm.getCreatedAt(), localDateTime);
                    String time = "";

                    if(duration.toHours() == 0) {
                        time = duration.toMinutes() + "분 전";
                    } else if (duration.toDays() == 0) {
                        time = duration.toHours() + "시간 전";
                    } else if (duration.toDays() < 30) {
                        time = duration.toDays() + "일 전";
                    } else {
                        time = ChronoUnit.MONTHS.between(fcm.getCreatedAt(), localDateTime) + "달 전";
                    }

                    FcmResponses fcmResponse = FcmResponses.fromEntity(fcm, text, time);
                    fcmResponses.add(fcmResponse);
                }
        );

        return null;
    }

    // 메세지 전송
    @Transactional
    @Override
    public void sendMessageTo(final long memberId, FcmResponse fcmResponse)  {
        try{
            String message = createMessage(memberId, fcmResponse);
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getAccessToken());

            HttpEntity entity = new HttpEntity<>(message, headers);

            String API_URL = fcmUrl;
            ResponseEntity response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

            saveFcmInfo(memberId, fcmResponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //db에 알림 저장
    @Transactional
    public void saveFcmInfo(final long memberId, FcmResponse fcmResponse) {
        FcmDataResponse fcmDataResponse = FcmDataResponse.fromFcmResponse(fcmResponse, memberId);
        Fcm fcm = fcmDataResponse.toEntity(fcmDataResponse);
        fcmRepository.save(fcm);
        log.info("save fcm");
    }

    // 메세지 생성
    @Transactional
    public String createMessage(final long memberId, FcmResponse fcmResponse) throws JsonProcessingException {
        FcmSendRequest fcmSendRequest = makeMessage(memberId, fcmResponse);

        ObjectMapper om = new ObjectMapper();

        FcmNotificationRequest fcmNotificationRequest = new FcmNotificationRequest(fcmSendRequest.notification().title(), fcmSendRequest.notification().body());
        FcmDataRequest fcmDataRequest = new FcmDataRequest();
        FcmMessageRequest fcmMessageRequest = new FcmMessageRequest(fcmDataRequest, fcmNotificationRequest, fcmSendRequest.token());

        FcmRequest fcmRequest = FcmRequest.toRequest(fcmMessageRequest, false);
        return om.writeValueAsString(fcmRequest);
    }


    // 메세지 문구 생성 로직
    @Transactional
    public FcmSendRequest makeMessage(final long memberId, FcmResponse fcmResponse) {
        MemberToken memberToken = memberTokenRepository.findByMemberId(memberId).orElseThrow();
        String token = memberToken.getFcm_token();
        switch (fcmResponse.fcmConstant()){
            case EVENTTOASTSPREAD:
                FcmNotificationRequest eventToastSpreadNotification = new FcmNotificationRequest(fcmResponse.nickname()+" 님이"+EVENTTOASTSPREAD.value(), fcmResponse.toastName());
                FcmSendRequest eventToastSpreadSend = new FcmSendRequest(token, eventToastSpreadNotification, new FcmDataRequest());
                return eventToastSpreadSend;
            case EVENTTOASTOPENED:
                FcmNotificationRequest eventToastOpenedNotification = new FcmNotificationRequest(EVENTTOASTOPENED.value(), fcmResponse.toastName());
                FcmSendRequest eventToastOpenedSend = new FcmSendRequest(token, eventToastOpenedNotification, new FcmDataRequest());
                return eventToastOpenedSend;
            case GIFTTOASTCREATED:
                FcmNotificationRequest giftToastCreatedNotification = new FcmNotificationRequest(GIFTTOASTCREATED.value(), fcmResponse.toastName());
                FcmSendRequest giftToastCreatedSend = new FcmSendRequest(token, giftToastCreatedNotification, new FcmDataRequest());
                return giftToastCreatedSend;
            case GIFTTOASTOPENED:
                FcmNotificationRequest giftToastOpenedNotification = new FcmNotificationRequest(GIFTTOASTOPENED.value(), fcmResponse.toastName());
                FcmSendRequest giftToastOpenedSend = new FcmSendRequest(token, giftToastOpenedNotification, new FcmDataRequest());
                return giftToastOpenedSend;
            case GIFTTOASTBAKED:
                FcmNotificationRequest giftToastBakedNotification = new FcmNotificationRequest(fcmResponse.nickname()+" 님이"+GIFTTOASTBAKED.value(), fcmResponse.toastName());
                FcmSendRequest giftToastBakedSend = new FcmSendRequest(token, giftToastBakedNotification, new FcmDataRequest());
                return giftToastBakedSend;
            case FOLLOW:
                FcmNotificationRequest followNotification = new FcmNotificationRequest(fcmResponse.nickname()+" 님이"+FOLLOW.value(), null);
                FcmSendRequest followSend = new FcmSendRequest(token, followNotification, new FcmDataRequest());
                return followSend;

            default:
                return null;
        }
    }

    // 접근 위한 엑세스 코드 생성
    @Transactional
    public String getAccessToken()  {
        try {
            String firebaseConfigPath = fcmPath;

            GoogleCredentials googleCredentials = GoogleCredentials
                    .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                    .createScoped(List.of(fcmCredential));

            googleCredentials.refreshIfExpired();
            return googleCredentials.getAccessToken().getTokenValue();
        } catch (Exception e) {}
        return null;

    }
}


