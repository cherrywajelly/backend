package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.dto.fcm.requset.FcmSendRequest;
import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;


public interface FcmService {
    void sendMessageTo(FcmDataResponse fcmDataResponse);
}
