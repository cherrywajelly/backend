package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface IconService {
    Map<MultipartFile, String> postIconSet(List<MultipartFile> files, long iconGroupId);
}
