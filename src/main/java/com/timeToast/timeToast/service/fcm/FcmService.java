package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;

import java.util.List;


public interface FcmService {
    void saveToken(final long memberId, final String token);

    List<FcmResponses> getFcmResponses(final long memberId);

    void sendMessageTo(final long memberId, FcmResponse fcmDataResponse);
}
