package com.blogapp.blogappbackend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MediaService {

    @Value("${upload.dir:uploads}") // default = uploads folder
    private String uploadDir;

    // FETCH
    public byte[] getFile(String fileType, String fileName) throws IOException {
        Path filePath = Paths.get(uploadDir, fileType).resolve(fileName).normalize();

        if(!Files.exists(filePath)) {
            throw new NoSuchFileException("File not found: " + fileName);
        }
        return Files.readAllBytes(filePath);
    }

    public String getContentType(String folder, String filename) throws IOException {
        Path filePath = Paths.get(uploadDir, folder).resolve(filename).normalize();
        if (!Files.exists(filePath)) {
            throw new NoSuchFileException("File not found: " + filename);
        }
        return Files.probeContentType(filePath);
    }

    //UPLOAD
    public List<String> uploadFiles (List<MultipartFile> files) {
        List<String> fileUrls = new ArrayList<>();

        try {

            // Create Root MEDIA folder if it doesn't exist
//            Path folderPath = Paths.get(uploadDir, folder);
//            Files.createDirectories(folderPath);


            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                // Check ContentType
                String contentType = file.getContentType();
                String folder = "others"; // default

                if (contentType.startsWith("image/")) folder = "images";
                else if (contentType.startsWith("video/")) folder = "videos";
                else if (contentType.startsWith("audio/")) folder = "audios";

                // Create MEDIA folders if it doesn't exist
                Path folderPath = Paths.get(uploadDir, folder);
                Files.createDirectories(folderPath); // ensures folder exists

                // Generate unique file name
                String originalFilename = file.getOriginalFilename();
                String uniqueFileName = System.currentTimeMillis() + "_" + originalFilename;

                // Save file
                Path filePath = folderPath.resolve(uniqueFileName);
                Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Construct public URL
                String fileUrl = "/api/media/" + uploadDir + "/" + folder + "/" + uniqueFileName;
                fileUrls.add(fileUrl);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload files: " + e.getMessage());
        }

        return fileUrls;
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex >= 0) ? filename.substring(dotIndex + 1).toLowerCase() : "";
    }

    private boolean isVideo(String extension) {
        return List.of("mp4", "mov", "avi", "mkv").contains(extension);
    }

}
