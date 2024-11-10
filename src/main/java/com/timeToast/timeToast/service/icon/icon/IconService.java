package com.timeToast.timeToast.service.icon.icon;

import com.timeToast.timeToast.domain.icon.icon.Icon;
import com.timeToast.timeToast.dto.icon.icon.request.IconPostRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IconService {
    void postIconSet(List<MultipartFile> files, long iconGroupId);
    Icon getNotOpenIcon();
}
