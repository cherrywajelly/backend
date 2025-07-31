package com.timeToast.timeToast.global.response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public record SuccessResponse(
        Long id,
        String message,
        String statusCode
) {
    public static SuccessResponse withId(final Long memberId, final Long id, final String statusCode, final String message) {
        log.info("Success Response: memberId={}, id={}, message={}", memberId, id, message);
        return new SuccessResponse(id, statusCode, message);
    }

    public static Response withoutId(final Long memberId, final Long id, final String statusCode, final String message) {
        log.info("Success Response: memberId={}, id={}, message={}", memberId, id, message);
        return new Response(statusCode, message);
    }
}
