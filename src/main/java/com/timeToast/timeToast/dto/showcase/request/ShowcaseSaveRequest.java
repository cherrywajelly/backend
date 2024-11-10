package com.timeToast.timeToast.dto.showcase.request;

import java.util.List;
public record ShowcaseSaveRequest(
        List<Long> showcases
) {
}
