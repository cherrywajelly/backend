package com.timeToast.timeToast.service.icon.icon;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IconService {
    void postIconSet(List<MultipartFile> files, long iconGroupId);
}
