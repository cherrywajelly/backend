package com.timeToast.timeToast.service.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.timeToast.timeToast.domain.fcm.Fcm;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.dto.fcm.requset.*;
import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmLinkResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.repository.event_toast.EventToastRepository;
import com.timeToast.timeToast.repository.fcm.FcmRepository;
import com.timeToast.timeToast.repository.gift_toast.gift_toast.GiftToastRepository;
import com.timeToast.timeToast.repository.icon.icon.IconRepository;
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
import org.springframework.web.servlet.view.RedirectView;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private final EventToastRepository eventToastRepository;
    private final IconRepository iconRepository;
    private final GiftToastRepository giftToastRepository;
    private final MemberRepository memberRepository;


    @Transactional
    @Override
    public void saveToken(final long memberId, final String token){
        MemberToken memberToken = memberTokenRepository.findByMemberId(memberId).orElseThrow();
        if (token != null) {
            memberToken.updateFcmToken(token);
            memberTokenRepository.save(memberToken);
            log.info("update fcm token");
        } else {
            throw new BadRequestException(INVALID_FCM_TOKEN.getMessage());
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<FcmResponses> getFcmResponses(final long memberId){
        List<FcmResponses> fcmResponses = new ArrayList<>();
        List<Fcm> fcms = fcmRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
        fcms.forEach(
                fcm -> {
                    String text = fcm.getFcmConstant().value();
                    LocalDateTime localDateTime = LocalDateTime.now();
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

                    FcmResponses fcmResponse = FcmResponses.fromEntity(fcm, text, time, fcm.getParam());
                    fcmResponses.add(fcmResponse);
                }
        );

        return fcmResponses;
    }

    // 알림 눌렀을 때
    @Transactional
    @Override
    public void putIsOpened(final long memberId, final long fcmId) {
        Fcm fcm = fcmRepository.getById(fcmId);

        if (!fcm.isOpened()) {
            fcm.updateIsOpened(true);
            fcmRepository.save(fcm);
        }
    }

    // 메세지 전송
    @Transactional
    @Override
    public void sendMessageTo(final long memberId, final FcmResponse fcmResponse)  {
        try{
            String message = createMessage(memberId, fcmResponse);

            if (message != null) {
                RestTemplate restTemplate = new RestTemplate();

                restTemplate.getMessageConverters()
                        .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.set("Authorization", "Bearer " + getAccessToken());

                HttpEntity entity = new HttpEntity<>(message, headers);

                String API_URL = fcmUrl;
                restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
                saveFcmInfo(memberId, fcmResponse);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    //db에 알림 저장
    @Transactional
    public void saveFcmInfo(final long memberId, final FcmResponse fcmResponse) {
        FcmDataResponse fcmDataResponse = FcmDataResponse.fromFcmResponse(fcmResponse, memberId);
        String imageUrl = "";

        switch (fcmResponse.fcmConstant()) {
            case EVENTTOASTSPREAD:
                imageUrl = iconRepository.getById(eventToastRepository.getById(fcmDataResponse.param()).getIconId()).getIconImageUrl();
                break;
            case EVENTTOASTOPENED:
                imageUrl = iconRepository.getById(eventToastRepository.getById(fcmDataResponse.param()).getIconId()).getIconImageUrl();
                break;
            case GIFTTOASTCREATED:
                imageUrl = iconRepository.getById(giftToastRepository.getById(fcmDataResponse.param()).getIconId()).getIconImageUrl();
                break;
            case GIFTTOASTOPENED:
                imageUrl = iconRepository.getById(giftToastRepository.getById(fcmDataResponse.param()).getIconId()).getIconImageUrl();
                break;
            case GIFTTOASTBAKED:
                imageUrl = iconRepository.getById(giftToastRepository.getById(fcmDataResponse.param()).getIconId()).getIconImageUrl();
                break;
            case FOLLOW:
                imageUrl = memberRepository.getById(fcmDataResponse.param()).getMemberProfileUrl();
                break;
            default:
                imageUrl = null;
        }

        Fcm fcm = fcmDataResponse.toEntity(fcmDataResponse, imageUrl);
        fcmRepository.save(fcm);
        log.info("save fcm");
    }

    // 메세지 생성
    @Transactional
    public String createMessage(final long memberId, FcmResponse fcmResponse) throws JsonProcessingException {
        Optional<FcmSendRequest> fcmSendRequest = makeMessage(memberId, fcmResponse);

        if(fcmSendRequest.isPresent()){
            ObjectMapper om = new ObjectMapper();

            FcmNotificationRequest fcmNotificationRequest = new FcmNotificationRequest(fcmSendRequest.get().notification().title(), fcmSendRequest.get().notification().body());
            FcmMessageRequest fcmMessageRequest = new FcmMessageRequest(fcmSendRequest.get().data(), fcmNotificationRequest, fcmSendRequest.get().token());

            FcmRequest fcmRequest = FcmRequest.toRequest(fcmMessageRequest, false);

            if (fcmSendRequest.get().token() != null) {
                return om.writeValueAsString(fcmRequest);
            }
        }
        return null;
    }


    // 메세지 문구 생성 로직
    @Transactional
    public Optional<FcmSendRequest> makeMessage(final long memberId, FcmResponse fcmResponse) {
        Optional<MemberToken> memberToken = memberTokenRepository.findByMemberId(memberId);

        if(memberToken.isPresent()){
            String token = memberToken.get().getFcm_token();
            switch (fcmResponse.fcmConstant()){
                case EVENTTOASTSPREAD:
                    FcmNotificationRequest eventToastSpreadNotification = new FcmNotificationRequest(fcmResponse.nickname()+" 님이"+EVENTTOASTSPREAD.value(), fcmResponse.toastName());
                    return Optional.of(new FcmSendRequest(token, eventToastSpreadNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmResponse.param()))));
                case EVENTTOASTOPENED:
                    FcmNotificationRequest eventToastOpenedNotification = new FcmNotificationRequest(EVENTTOASTOPENED.value(), fcmResponse.toastName());
                    return Optional.of( new FcmSendRequest(token, eventToastOpenedNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmResponse.param()))));
                case GIFTTOASTCREATED:
                    FcmNotificationRequest giftToastCreatedNotification = new FcmNotificationRequest(GIFTTOASTCREATED.value(), fcmResponse.toastName());
                    return Optional.of(new FcmSendRequest(token, giftToastCreatedNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmResponse.param()))));
                case GIFTTOASTOPENED:
                    FcmNotificationRequest giftToastOpenedNotification = new FcmNotificationRequest(GIFTTOASTOPENED.value(), fcmResponse.toastName());
                    return Optional.of(new FcmSendRequest(token, giftToastOpenedNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmResponse.param()))));
                case GIFTTOASTBAKED:
                    FcmNotificationRequest giftToastBakedNotification = new FcmNotificationRequest(fcmResponse.nickname()+" 님이"+GIFTTOASTBAKED.value(), fcmResponse.toastName());
                    return Optional.of(new FcmSendRequest(token, giftToastBakedNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmResponse.param()))));
                case FOLLOW:
                    FcmNotificationRequest followNotification = new FcmNotificationRequest(fcmResponse.nickname()+" 님이"+FOLLOW.value(), null);
                    return Optional.of(new FcmSendRequest(token, followNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmResponse.param()))));
                default:
                    return Optional.empty();
                }

        }
        return Optional.empty();

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


