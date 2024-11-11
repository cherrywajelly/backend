package com.timeToast.timeToast.global.constant;


import org.springframework.beans.factory.annotation.Value;

public class BasicImage {

    @Value("https://objectstorage.ap-chuncheon-1.oraclecloud.com/n/axmpikvsv3z9/b/timetoast_bucket/o/basic%2Fimage%2Fprofile")
    public static String basicProfileImageUrl;

    @Value("https://objectstorage.ap-chuncheon-1.oraclecloud.com/n/axmpikvsv3z9/b/timetoast_bucket/o/basic%2Fimage%2Funopened")
    public static String notOpenImageUrl;
}
