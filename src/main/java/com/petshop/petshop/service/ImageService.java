package com.petshop.petshop.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    String saveImageToServer(MultipartFile image);
    void deleteImageFromServer(String imageUrl);
    void initializeUploadDirectory();
}
