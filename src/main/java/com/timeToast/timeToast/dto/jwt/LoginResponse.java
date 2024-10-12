package com.timeToast.timeToast.dto.jwt;

public record LoginResponse(
        String accessToken,
        String refreshToken
) {
}
