package com.timeToast.timeToast.service.fcm;

import com.timeToast.timeToast.domain.enums.fcm.FcmConstant;
import com.timeToast.timeToast.dto.fcm.requset.FcmPostRequest;
import com.timeToast.timeToast.dto.fcm.response.FcmResponse;
import com.timeToast.timeToast.dto.fcm.response.FcmResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;

import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class FcmServiceTest implements FcmService {

    @Override
    public Response saveToken(final long memberId, final String token){
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public FcmResponses getFcmResponses(final long memberId){
        List<FcmResponse> fcmResponses = new ArrayList<>();
        fcmResponses.add(new FcmResponse(1, FcmConstant.EVENTTOASTSPREAD, "nickname", "text", "imageUrl", "time", "toastName", false, 1));

        return new FcmResponses(fcmResponses);
    }

    @Override
    public Response sendMessageTo(final long memberId, FcmPostRequest fcmPostRequest){
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

    @Override
    public Response putIsOpened(final long memberId, final long fcmId){
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());

    }
}