package com.timeToast.timeToast.dto.gift_toast.request;

import java.time.LocalDate;

public record GiftToastRequest (
        LocalDate memorizedDate,
        LocalDate openedDate,
        boolean isOpened
) {
}
