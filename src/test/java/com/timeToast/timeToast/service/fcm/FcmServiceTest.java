package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.dto.fcm.response.FcmOpenedResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;

import java.util.List;

public class FcmServiceTest implements FcmService {

    @Override
    public void saveToken(final long memberId, final String token){

    }

    @Override
    public List<FcmResponses> getFcmResponses(final long memberId){
        return null;
    }

    @Override
    public void sendMessageTo(final long memberId, FcmResponse fcmResponse){

    }

    @Override
    public FcmOpenedResponse putIsOpened(final long memberId, final long fcmId){
        return null;
    }
}
