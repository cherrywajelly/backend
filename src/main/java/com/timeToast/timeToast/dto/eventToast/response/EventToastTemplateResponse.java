package com.timeToast.timeToast.dto.eventToast.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record EventToastTemplateResponse (
        String title,

        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate openedDate

 ) {
}