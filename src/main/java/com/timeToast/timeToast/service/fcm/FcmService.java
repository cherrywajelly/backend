package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.response.Response;

public interface FcmService {
    Response saveToken(final long memberId, final String token);
    FcmResponses getFcmResponses(final long memberId);
    Response sendMessageTo(final long memberId, FcmPostRequest fcmPostRequest);
    Response putIsOpened(final long memberId, final long fcmId);
}
