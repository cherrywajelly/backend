package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.dto.fcm.response.FcmLinkResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


public interface FcmService {
    Response saveToken(final long memberId, final String token);

    //알림 조회
    List<FcmResponses> getFcmResponses(final long memberId);

    // 알림 생성할 때 사용
    Response sendMessageTo(final long memberId, FcmResponse fcmResponse);

    // 읽었는지 체크
    Response putIsOpened(final long memberId, final long fcmId);
}
