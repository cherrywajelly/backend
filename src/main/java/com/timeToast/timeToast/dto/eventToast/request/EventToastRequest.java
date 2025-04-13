package com.timeToast.timeToast.dto.eventToast.request;

import java.time.LocalDate;

public record EventToastRequest (
        LocalDate openedDate,
        boolean isOpened
){
}
