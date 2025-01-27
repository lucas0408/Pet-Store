package com.petshop.petshop.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;  // Add this import
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service  // Add this annotation
public class ImageServiceImpl implements ImageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct
    @Override
    public void initializeUploadDirectory() {
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar o diretório de upload", e);
        }
    }

    @Override
    public void deleteImageFromServer(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                String fileName = imageUrl.substring("/images/".length());
                Path imagePath = Paths.get(uploadDir, fileName);

                if (Files.exists(imagePath)) {
                    Files.delete(imagePath);
                }
            } catch (IOException e) {
                throw new RuntimeException("Erro ao deletar imagem do servidor: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public String saveImageToServer(MultipartFile image) {
        if (image != null && !image.isEmpty()) {
            try {
                String fileName = UUID.randomUUID().toString() + "_" +
                        image.getOriginalFilename().replaceAll("[^a-zA-Z0-9.]", "_");

                Path filePath = Paths.get(uploadDir, fileName);

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                    return "/images/" + fileName;
                }
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar imagem no servidor: " + e.getMessage(), e);
            }
        }
        return null;
    }
}