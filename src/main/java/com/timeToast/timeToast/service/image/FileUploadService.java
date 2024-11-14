package com.timeToast.timeToast.service.image;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadfile(MultipartFile file, String endpoint);
}
