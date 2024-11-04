package com.timeToast.timeToast.dto.member.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class KakaoUserDataDto {
    private String iss;
    private String aud;
    private String sub;
    private Integer iat;
    private Integer exp;
    private Integer auth_time;
    private String nickname;
    private String email;
}
