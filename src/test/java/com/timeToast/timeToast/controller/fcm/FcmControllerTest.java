package com.timeToast.timeToast.controller.fcm;

import com.timeToast.timeToast.service.fcm.FcmService;
import com.timeToast.timeToast.service.fcm.FcmServiceTest;
import com.timeToast.timeToast.util.BaseControllerTests;

public class FcmControllerTest extends BaseControllerTests {
    private final FcmService fcmService = new FcmServiceTest();

    @Override
    protected Object initController() {
        return new FcmController(fcmService);
    }
}
