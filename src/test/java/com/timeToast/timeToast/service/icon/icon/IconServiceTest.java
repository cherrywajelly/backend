package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.global.constant.StatusCode;
import com.timeToast.timeToast.global.response.Response;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.timeToast.timeToast.global.constant.SuccessConstant.SUCCESS_POST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class IconServiceTest implements IconService{

    @Override
    public void postIconSet(List<MultipartFile> files, long iconGroupId) {
//        Map<MultipartFile, String> result = new HashMap<>();
//        result.put(mock(MockMultipartFile.class), "imageUrl");
//        return result;
    }

}