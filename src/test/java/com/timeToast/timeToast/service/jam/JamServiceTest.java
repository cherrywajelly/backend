package com.timeToast.timeToast.service.jam;

import com.timeToast.timeToast.dto.event_toast.response.EventToastDataResponse;
import com.timeToast.timeToast.dto.jam.request.JamRequest;
import com.timeToast.timeToast.dto.jam.response.JamDataResponse;
import com.timeToast.timeToast.dto.jam.response.JamDetailResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponse;
import com.timeToast.timeToast.dto.jam.response.JamResponses;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_DELETE;
import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;

public class JamServiceTest implements JamService {

    @Override
    public Response postJam(JamRequest jamRequest, MultipartFile contents, MultipartFile image, final long eventToastId, final long memberId){
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }


    @Override
    public JamResponses getJams(final long eventToastId) {
        List<JamResponse> jamResponses = new ArrayList<>();
        jamResponses.add(new JamResponse(1, "imageUrl", "nickname"));
        return new JamResponses(jamResponses);
    }


    @Override
    public JamDetailResponse getJam(final long memberId, final long jamId){
        EventToastDataResponse eventToastDataResponse = new EventToastDataResponse("e.title", "e.memberProfileUrl",
                "e.nickname", "e.iconImageUrl");
        JamDataResponse jamDataResponse = new JamDataResponse("j.iconImageUrl", "j.title", "j.memberProfileUrl",
                "j.nickname", "j.contentUrl", "j.imageUrl", LocalDate.of(2024, 11, 11));

        JamDetailResponse jamDetailResponse = new JamDetailResponse(eventToastDataResponse, jamDataResponse);
        return jamDetailResponse;
    }


    @Override
    public Response deleteJam(final long memberId, final long jamId){
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_DELETE.getMessage());
    }
}
