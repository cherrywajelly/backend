package com.timeToast.timeToast.dto.event_toast.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.timeToast.timeToast.domain.event_toast.EventToast;
import com.timeToast.timeToast.dto.jam.response.JamManagerResponse;
import com.timeToast.timeToast.dto.jam.response.JamManagerResponses;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record EventToastInfoManagerResponse (
    long eventToastId,
    String iconImageUrl,
    String title,
    String nickname,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate openedDate,
    boolean isOpened,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate createdAt,
    List<JamManagerResponse> jamManagerResponses
){
    public static EventToastInfoManagerResponse from(final EventToast eventToast, final String iconImageUrl, final String nickname, final List<JamManagerResponse> jamManagerResponses){
        return EventToastInfoManagerResponse.builder()
                .eventToastId(eventToast.getId())
                .iconImageUrl(iconImageUrl)
                .title(eventToast.getTitle())
                .nickname(nickname)
                .openedDate(eventToast.getOpenedDate())
                .isOpened(eventToast.isOpened())
                .createdAt(eventToast.getCreatedAt().toLocalDate())
                .jamManagerResponses(jamManagerResponses)
                .build();
    }
}