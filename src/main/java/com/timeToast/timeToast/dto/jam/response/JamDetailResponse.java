package com.timeToast.timeToast.dto.jam.response;

import com.timeToast.timeToast.dto.event_toast.response.EventToastDataResponse;
import lombok.Builder;

@Builder
public record JamDetailResponse(

        EventToastDataResponse eventToastDataResponse,
        JamDataResponse jamDataResponse

){
    public static JamDetailResponse of(EventToastDataResponse eventToastDataResponse, JamDataResponse jamDataResponse) {
        return JamDetailResponse.builder()
                .eventToastDataResponse(eventToastDataResponse)
                .jamDataResponse(jamDataResponse)
                .build();
    }

}