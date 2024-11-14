package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.dto.fcm.response.FcmLinkResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;

import java.util.ArrayList;
import java.util.List;

public class FcmServiceTest implements FcmService {

    @Override
    public void saveToken(final long memberId, final String token){

    }

    @Override
    public List<FcmResponses> getFcmResponses(final long memberId){
        List<FcmResponses> fcmResponses = new ArrayList<>();
        fcmResponses.add(new FcmResponses(1, FcmConstant.EVENTTOASTSPREAD, "nickname", "text", "imageUrl", "time", "toastName", false));

        return fcmResponses;
    }

    @Override
    public void sendMessageTo(final long memberId, FcmResponse fcmResponse){

    }

    @Override
    public FcmLinkResponse putIsOpened(final long memberId, final long fcmId){
        FcmLinkResponse fcmLinkResponse = new FcmLinkResponse(FcmConstant.EVENTTOASTSPREAD.toString(), "1");
        return fcmLinkResponse;
    }
}