package com.petshop.petshop.DTO;

import com.petshop.petshop.model.Category;
import org.springframework.web.multipart.MultipartFile;

public record CategoryDTO(
        String id,
        String name,
        String imageUrl,
        MultipartFile image
) {

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String imageUrl() {
        return imageUrl;
    }

    @Override
    public MultipartFile image() {
        return image;
    }
}