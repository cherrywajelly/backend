package com.timeToast.timeToast.dto.jam.response;

import com.timeToast.timeToast.dto.event_toast.response.member.EventToastJamResponse;
import lombok.Builder;

//TODO 삭제 필요
@Builder
public record JamDetailResponse(

        EventToastJamResponse eventToastJamResponse,
        JamDataResponse jamDataResponse

){
    public static JamDetailResponse of(EventToastJamResponse eventToastJamResponse, JamDataResponse jamDataResponse) {
        return JamDetailResponse.builder()
                .eventToastJamResponse(eventToastJamResponse)
                .jamDataResponse(jamDataResponse)
                .build();
    }

}