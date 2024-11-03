package com.timeToast.timeToast.controller;

import com.timeToast.timeToast.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/api/v1")
@RestController
@RequiredArgsConstructor
public class FileUploadController {

    private final FileUploadService fileUploadService;


    @PostMapping(path = "/upload/{iconGroupId}")
    public ResponseEntity<Object> uploadFile(@RequestParam("files") List<MultipartFile> files,
                                             @PathVariable final long iconGroupId){

        try {
            System.out.println(files);
            fileUploadService.upload(files);
            System.out.println(files);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(path = "/file")
    public ResponseEntity<Object> getURl(@RequestBody List<String> iconIds){
        try {
            List<String> accessUris = fileUploadService.getFileObject(iconIds);
            return ResponseEntity.ok().body(accessUris);
        } catch (Exception e) {
            // TODO: handle exception
            return ResponseEntity.internalServerError().build();
        }
        //TODO accessUri를 db에 저장하기
    }
}