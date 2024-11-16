package com.timeToast.timeToast.global.constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component

public class BasicImage {
    public BasicImage(){

    }


    @Autowired
    public static String BASIC_PROFILE_IMAGE_URL;

    @Autowired
    public static String NOT_OPEN_IMAGE_URL;

    @Value("https://axmpikvsv3z9.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/axmpikvsv3z9/b/timetoast_bucket/o/basic%2Fimage%2Fprofile")
    public void setBasicProfileImageUrl(String basicProfileImageUrl){
        BASIC_PROFILE_IMAGE_URL = basicProfileImageUrl;
    }

    @Value("https://axmpikvsv3z9.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/axmpikvsv3z9/b/timetoast_bucket/o/basic%2Fimage%2Funopened")
    public void setNotOpenImageUrl(String notOpenImageUrl){
        NOT_OPEN_IMAGE_URL = notOpenImageUrl;
    }


}
