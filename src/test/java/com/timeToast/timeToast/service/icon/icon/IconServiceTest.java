package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.junit.jupiter.api.Assertions.*;

public class IconServiceTest implements IconService{

    @Override
    public Response postIconSet(List<MultipartFile> files, long iconGroupId) {
        return new Response(StatusCode.OK.getStatusCode(), SUCCESS_POST.getMessage());
    }

}