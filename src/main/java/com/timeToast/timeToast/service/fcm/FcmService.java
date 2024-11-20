package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.response.Response;

import java.util.List;


public interface FcmService {
    Response saveToken(final long memberId, final String token);
    FcmResponses getFcmResponses(final long memberId);
    Response sendMessageTo(final long memberId, FcmPostRequest fcmPostRequest);
    Response putIsOpened(final long memberId, final long fcmId);
}
