package com.timeToast.timeToast.dto.jam.response;

import com.timeToast.timeToast.dto.event_toast.response.EventToastDataResponse;
import lombok.Builder;

@Builder
public record JamResponse (

        EventToastDataResponse eventToastDataResponse,
        JamDataResponse jamDataResponse

){
    public static JamResponse of(EventToastDataResponse eventToastDataResponse, JamDataResponse jamDataResponse) {
        return JamResponse.builder()
                .eventToastDataResponse(eventToastDataResponse)
                .jamDataResponse(jamDataResponse)
                .build();
    }

}