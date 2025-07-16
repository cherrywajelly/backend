package com.timeToast.timeToast.global.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtExp {

    public JwtExp(){

    }

    public static long ACCESS_EXP;

    public static long REFRESH_EXP;

    @Value("${spring.jwt.access_exp_time}")
    public void setAccessExp(long accessExp) {
        ACCESS_EXP = accessExp;
    }

    @Value("${spring.jwt.refresh_exp_time}")
    public void setRefreshExp(long refreshExp) {
        REFRESH_EXP = refreshExp;
    }
}
