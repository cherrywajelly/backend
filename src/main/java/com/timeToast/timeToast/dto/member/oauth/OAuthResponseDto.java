package com.timeToast.timeToast.dto.member.oauth;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Getter
public class OAuthResponseDto {
    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String refresh_token;
    private String id_token;
}
