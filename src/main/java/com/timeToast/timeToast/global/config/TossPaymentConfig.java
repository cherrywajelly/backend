package com.timeToast.timeToast.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TossPaymentConfig {

    @Value("${payment.toss.client-key}")
    public static String TOSS_CLIENT_KEY;

    @Value("${payment.toss.secret-key}")
    public static String TOSS_SECRET_KEY;

    @Value("${payment.toss.security-key}")
    public static String TOSS_SECURITY_KEY;

}
