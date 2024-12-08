package com.timeToast.timeToast.global.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component

public class BasicImage {
    public BasicImage(){

    }

    public static String BASIC_PROFILE_IMAGE_URL;

    public static String NOT_OPEN_IMAGE_URL;

    @Value("${spring.cloud.oci.base-profile-image-url}")
    public void setBasicProfileImageUrl(String basicProfileImageUrl){
        BASIC_PROFILE_IMAGE_URL = basicProfileImageUrl;
    }

    @Value("${spring.cloud.oci.not-open-image-url}")
    public void setNotOpenImageUrl(String notOpenImageUrl){
        NOT_OPEN_IMAGE_URL = notOpenImageUrl;
    }


}
