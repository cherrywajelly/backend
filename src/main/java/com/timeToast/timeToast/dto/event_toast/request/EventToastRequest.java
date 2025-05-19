package com.timeToast.timeToast.dto.event_toast.request;

import java.time.LocalDate;

public record EventToastRequest (
        LocalDate openedDate,
        boolean isOpened
){
}
