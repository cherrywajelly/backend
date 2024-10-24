package com.timeToast.timeToast.dto.member.oauth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GoogleUserDataDto {

    private String name;
    private String email;
    private String iss;
    private String azp;
    private String aud;
    private String sub;
    private String hd;
    private Boolean email_verified;
    private String at_hash;
    private String picture;
    private String given_name;
    private String family_name;
    private String locale;
    private String iat;
    private String exp;
}
