package com.timeToast.timeToast.global.response;

public record Response(
        String statusCode,
        String message
) {
    public static Response of(final String statusCode, final String message) {
        return new Response(statusCode, message);
    }
}
