package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.dto.fcm.response.FcmLinkResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;


public interface FcmService {
    void saveToken(final long memberId, final String token);

    //알림 조회
    List<FcmResponses> getFcmResponses(final long memberId);

    // 알림 생성할 때 사용
    void sendMessageTo(final long memberId, FcmResponse fcmResponse);

    // 읽었는지 체크
    RedirectView putIsOpened(final long memberId, final long fcmId);
}
