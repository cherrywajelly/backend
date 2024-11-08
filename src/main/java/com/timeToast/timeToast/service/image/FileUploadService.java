package com.timeToast.timeToast.service.image;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadImages(MultipartFile file, String endpoint);

    String uploadTexts(String text, String endpoint);
}
