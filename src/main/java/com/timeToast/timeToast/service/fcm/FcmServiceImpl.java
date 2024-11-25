package com.timeToast.timeToast.service.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.timeToast.timeToast.domain.fcm.Fcm;
import com.timeToast.timeToast.domain.member.member_token.MemberToken;
import com.timeToast.timeToast.dto.fcm.requset.*;
import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmLinkResponse;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.exception.BadRequestException;
import com.timeToast.timeToast.global.response.Response;
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

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.timeToast.timeToast.domain.enums.fcm.FcmConstant.*;
import static com.timeToast.timeToast.global.constant.ExceptionConstant.INVALID_FCM_TOKEN;
import static com.timeToast.timeToast.global.constant.SuccessConstant.*;

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
    public Response saveToken(final long memberId, final String token){

        if (token != null) {

            fcmTokenValidation(memberId, token);

            MemberToken memberToken = memberTokenRepository.findByMemberId(memberId).orElseThrow(()-> new BadRequestException(INVALID_FCM_TOKEN.getMessage()));
            memberToken.updateFcmToken(token);
            memberTokenRepository.save(memberToken);

            log.info("update fcm token");

        } else {
            throw new BadRequestException(INVALID_FCM_TOKEN.getMessage());
        }
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }


    @Transactional
    public void fcmTokenValidation(final long memberId, final String token) {
        Optional<MemberToken> memberToken = memberTokenRepository.findByFcmToken(token);

        if (memberToken.isPresent()) {
            if (memberToken.get().getMemberId() != memberId) {
                memberToken.get().updateFcmToken(null);
                memberTokenRepository.save(memberToken.get());
                log.info("changed fcm token {} to {}", memberToken.get().getMemberId(), memberId);
            }
        }
    }


    @Transactional(readOnly = true)
    @Override
    public FcmResponses getFcmResponses(final long memberId){
        List<FcmResponse> fcmResponses = new ArrayList<>();
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

                    FcmResponse fcmResponse = FcmResponse.fromEntity(fcm, text, time, fcm.getParam());
                    fcmResponses.add(fcmResponse);
                }
        );

        return new FcmResponses(fcmResponses);
    }

    @Transactional
    @Override
    public Response putIsOpened(final long memberId, final long fcmId) {
        Fcm fcm = fcmRepository.getById(fcmId);

        if (!fcm.isOpened()) {
            fcm.updateIsOpened(true);
            fcmRepository.save(fcm);
        }
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_PUT.getMessage());
    }

    @Transactional
    @Override
    public Response sendMessageTo(final long memberId, final FcmPostRequest fcmPostRequest)  {
        try{
            String message = createMessage(memberId, fcmPostRequest);

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
                saveFcmInfo(memberId, fcmPostRequest);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());

    }

    @Transactional
    public Response saveFcmInfo(final long memberId, final FcmPostRequest fcmPostRequest) {
        FcmDataResponse fcmDataResponse = FcmDataResponse.fromFcmResponse(fcmPostRequest, memberId);
        String imageUrl = "";

        switch (fcmPostRequest.fcmConstant()) {
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
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Transactional
    public String createMessage(final long memberId, FcmPostRequest fcmPostRequest) throws JsonProcessingException {
        Optional<FcmSendRequest> fcmSendRequest = makeMessage(memberId, fcmPostRequest);

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

    @Transactional
    public Optional<FcmSendRequest> makeMessage(final long memberId, FcmPostRequest fcmPostRequest) {
        Optional<MemberToken> memberToken = memberTokenRepository.findByMemberId(memberId);

        if(memberToken.isPresent()){
            String token = memberToken.get().getFcmToken();
            switch (fcmPostRequest.fcmConstant()){
                case EVENTTOASTSPREAD:
                    FcmNotificationRequest eventToastSpreadNotification = new FcmNotificationRequest(fcmPostRequest.nickname()+" 님이"+EVENTTOASTSPREAD.value(), fcmPostRequest.toastName());
                    return Optional.of(new FcmSendRequest(token, eventToastSpreadNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmPostRequest.param()))));
                case EVENTTOASTOPENED:
                    FcmNotificationRequest eventToastOpenedNotification = new FcmNotificationRequest(EVENTTOASTOPENED.value(), fcmPostRequest.toastName());
                    return Optional.of( new FcmSendRequest(token, eventToastOpenedNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmPostRequest.param()))));
                case GIFTTOASTCREATED:
                    FcmNotificationRequest giftToastCreatedNotification = new FcmNotificationRequest(GIFTTOASTCREATED.value(), fcmPostRequest.toastName());
                    return Optional.of(new FcmSendRequest(token, giftToastCreatedNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmPostRequest.param()))));
                case GIFTTOASTOPENED:
                    FcmNotificationRequest giftToastOpenedNotification = new FcmNotificationRequest(GIFTTOASTOPENED.value(), fcmPostRequest.toastName());
                    return Optional.of(new FcmSendRequest(token, giftToastOpenedNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmPostRequest.param()))));
                case GIFTTOASTBAKED:
                    FcmNotificationRequest giftToastBakedNotification = new FcmNotificationRequest(fcmPostRequest.nickname()+" 님이"+GIFTTOASTBAKED.value(), fcmPostRequest.toastName());
                    return Optional.of(new FcmSendRequest(token, giftToastBakedNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmPostRequest.param()))));
                case FOLLOW:
                    FcmNotificationRequest followNotification = new FcmNotificationRequest(fcmPostRequest.nickname()+" 님이"+FOLLOW.value(), null);
                    return Optional.of(new FcmSendRequest(token, followNotification, new FcmLinkResponse(EVENTTOASTSPREAD.toString(), Long.toString(fcmPostRequest.param()))));
                default:
                    return Optional.empty();
                }

        }
        return Optional.empty();

    }

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


