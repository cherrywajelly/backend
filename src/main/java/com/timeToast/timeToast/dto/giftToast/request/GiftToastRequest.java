package com.timeToast.timeToast.dto.giftToast.request;

import java.time.LocalDate;

public record GiftToastRequest (
        LocalDate memorizedDate,
        LocalDate openedDate,
        boolean isOpened
) {
}
