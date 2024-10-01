package com.timeToast.timeToast.global.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class JwtKey {

    public JwtKey(){

    }

    @Autowired
    public static String JWT_KEY;

    @Value("${spring.jwt.key}")
    public void setJwtKey(String jwtKey) {
        JWT_KEY = jwtKey;
    }


}
