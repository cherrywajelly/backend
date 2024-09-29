package com.timeToast.timeToast.global.constant;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtKey {

    public JwtKey(){

    }

    @Autowired
    public static String Jwt_Key;

    @Value("${spring.jwt.key}")
    public void setJwtKey(String jwtKey) {
        jwtKey = jwtKey;
    }


}
