package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.dto.fcm.response.FcmResponse;


public interface FcmService {
    void saveToken(final long memberId, final String token);

    void sendMessageTo(final long memberId, FcmResponse fcmDataResponse);
}
