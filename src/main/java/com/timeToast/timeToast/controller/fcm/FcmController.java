package com.timeToast.timeToast.controller.fcm;

import com.timeToast.timeToast.dto.fcm.requset.FcmSendRequest;
import com.timeToast.timeToast.dto.fcm.response.FcmDataResponse;
import com.timeToast.timeToast.service.fcm.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequestMapping("/api/v1/fcm")
@RestController
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/send")
    public void pushMessage(@RequestBody FcmDataResponse fcmDataResponse) throws IOException {
        fcmService.sendMessageTo(fcmDataResponse);
    }
}
