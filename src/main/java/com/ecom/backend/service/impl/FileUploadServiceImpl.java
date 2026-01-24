package com.ecom.backend.service.impl;

import com.ecom.backend.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Override
    public String uploadImage(String path, MultipartFile productImage) throws IOException {

        System.out.println("Upload path: " + path);

        String originalFileName = productImage.getOriginalFilename();
        if (originalFileName == null || !originalFileName.contains(".")) {
            throw new IllegalArgumentException("Invalid file name");
        }

        String fileName = UUID.randomUUID() +
                originalFileName.substring(originalFileName.lastIndexOf('.'));

        Path uploadPath = Paths.get(path);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
            System.out.println("Directory created: " + uploadPath.toAbsolutePath());
        }

        Path filePath = uploadPath.resolve(fileName);

        Files.copy(
                productImage.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }
}
