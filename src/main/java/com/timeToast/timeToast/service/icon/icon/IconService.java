package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.global.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IconService {
    Response postIconSet(List<MultipartFile> files, long iconGroupId);
}
