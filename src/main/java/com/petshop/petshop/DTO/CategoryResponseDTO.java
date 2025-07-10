package com.petshop.petshop.DTO;

import com.petshop.petshop.model.Category;
import com.petshop.petshop.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.stream.Collectors;

public record CategoryResponseDTO(
        String id,
        String name,
        String imageUrl
) {

    public CategoryResponseDTO(Category category) {
        this(
                category.getId(),
                category.getName(),
                category.getImageUrl()
        );
    }

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

}