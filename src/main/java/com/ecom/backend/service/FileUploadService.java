package com.ecom.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {

    public String uploadImage(String path, MultipartFile productImage) throws IOException;
}
