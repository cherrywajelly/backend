package com.timeToast.timeToast.service.fcm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.timeToast.timeToast.dto.fcm.requset.*;
import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.timeToast.timeToast.global.constant.FcmConstant.*;

@Service
public class FcmServiceImpl implements FcmService {

    @Value("${fcm.url}")
    private String fcmUrl;

    @Value("${fcm.path}")
    private String fcmPath;

    @Value("${fcm.credential}")
    private String fcmCredential;

    // 메세지 전송
    @Override
    public void sendMessageTo(FcmDataResponse fcmDataResponse)  {
        try{
            String message = createMessage(fcmDataResponse);
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters()
                    .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + getAccessToken());

            HttpEntity entity = new HttpEntity<>(message, headers);

            String API_URL = fcmUrl;
            ResponseEntity response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    // 메세지 생성
    private String createMessage(FcmDataResponse fcmDataResponse) throws JsonProcessingException {
        FcmSendRequest fcmSendRequest = makeMessage(fcmDataResponse);

        ObjectMapper om = new ObjectMapper();

        FcmNotificationRequest fcmNotificationRequest = new FcmNotificationRequest(fcmSendRequest.notification().title(), fcmSendRequest.notification().body());
        FcmDataRequest fcmDataRequest = new FcmDataRequest();
        FcmMessageRequest fcmMessageRequest = new FcmMessageRequest(fcmDataRequest, fcmNotificationRequest, fcmSendRequest.token());

        FcmRequest fcmRequest = FcmRequest.toRequest(fcmMessageRequest, false);

        return om.writeValueAsString(fcmRequest);
    }


    // 메세지 문구 생성 로직
    public FcmSendRequest makeMessage(FcmDataResponse fcmDataResponse) {

        switch (fcmDataResponse.fcmConstant()){
            case EVENTTOASTSPREAD:
                FcmNotificationRequest eventToastSpreadNotification = new FcmNotificationRequest(fcmDataResponse.nickname()+" 님이"+EVENTTOASTSPREAD.value(), fcmDataResponse.toastName());
                FcmSendRequest eventToastSpreadSend = new FcmSendRequest("dzn0V1Jl5ZJN7VsVE4BZqg:APA91bELjYkR8gOlJTY7F1QZ3wFjPsf2VRek0MJ5PgC65o5yrz1FImjdZhauQd5yvk_VWZaXY2tiO1VzLsliG1WpbB_eQFemlnCSbNblZ_TZYvIKIs4r-Es", eventToastSpreadNotification, new FcmDataRequest());
                return eventToastSpreadSend;
            case EVENTTOASTOPENED:
                FcmNotificationRequest eventToastOpenedNotification = new FcmNotificationRequest(EVENTTOASTOPENED.value(), fcmDataResponse.toastName());
                FcmSendRequest eventToastOpenedSend = new FcmSendRequest("null", eventToastOpenedNotification, new FcmDataRequest());
                return eventToastOpenedSend;
            case GIFTTOASTCREATED:
                FcmNotificationRequest giftToastCreatedNotification = new FcmNotificationRequest(GIFTTOASTCREATED.value(), fcmDataResponse.toastName());
                FcmSendRequest giftToastCreatedSend = new FcmSendRequest("null", giftToastCreatedNotification, new FcmDataRequest());
                return giftToastCreatedSend;
            case GIFTTOASTOPENED:
                FcmNotificationRequest giftToastOpenedNotification = new FcmNotificationRequest(GIFTTOASTOPENED.value(), fcmDataResponse.toastName());
                FcmSendRequest giftToastOpenedSend = new FcmSendRequest("null", giftToastOpenedNotification, new FcmDataRequest());
                return giftToastOpenedSend;
            case GIFTTOASTBAKED:
                FcmNotificationRequest giftToastBakedNotification = new FcmNotificationRequest(fcmDataResponse.nickname()+" 님이"+GIFTTOASTBAKED.value(), fcmDataResponse.toastName());
                FcmSendRequest giftToastBakedSend = new FcmSendRequest("null", giftToastBakedNotification, new FcmDataRequest());
                return giftToastBakedSend;
            case FOLLOW:
                FcmNotificationRequest followNotification = new FcmNotificationRequest(fcmDataResponse.nickname()+" 님이"+FOLLOW.value(), null);
                FcmSendRequest followSend = new FcmSendRequest("null", followNotification, new FcmDataRequest());
                return followSend;

            default:
                return null;
        }
    }

    // 접근 위한 엑세스 코드 생성
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


