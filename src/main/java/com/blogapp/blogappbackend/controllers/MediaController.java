package com.blogapp.blogappbackend.controllers;

import com.blogapp.blogappbackend.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/media")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @Value("${upload.dir:uploads}") // default = uploads folder
    private String uploadDir;

    // UPLOAD MEDIA
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile (@RequestParam("file") List<MultipartFile> files) {
        List<String> fileUrls = mediaService.uploadFiles(files);
        return ResponseEntity.ok().body(Map.of("mediaUrls", fileUrls));
    }

    // FETCH MEDIA
    @GetMapping("/uploads/{type}/{filename:.+}")
    public ResponseEntity<?> getFile(@PathVariable String type, @PathVariable String filename) throws IOException {
        byte[] fileData = mediaService.getFile(type, filename);
        String contentType = mediaService.getContentType(type, filename);


        return ResponseEntity.ok()
                .header("Content-Type", contentType).body(fileData);
    }

}
