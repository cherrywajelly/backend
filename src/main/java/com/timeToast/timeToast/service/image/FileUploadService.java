package com.timeToast.timeToast.service.image;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String upload(MultipartFile file, String id);
}
