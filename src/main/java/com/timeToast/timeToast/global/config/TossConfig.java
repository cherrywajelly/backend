package com.timeToast.timeToast.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TossConfig {

    @Autowired
    public static String TOSS_SECRET_KEY;

    @Autowired
    public static String TOSS_CONFIRM_URL;

    @Value("${payment.toss.secret-key}")
    public void setTossSecretKey(String tossSecretKey) {
        TOSS_SECRET_KEY = tossSecretKey;
    }

    @Value("${payment.toss.confirm-url}")
    public void set(String tossConfirmUrl) {
        TOSS_CONFIRM_URL = tossConfirmUrl;
    }


}

