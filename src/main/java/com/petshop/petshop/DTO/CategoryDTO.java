package com.petshop.petshop.DTO;

import com.petshop.petshop.model.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

public class CategoryDTO {
    private String name;
    private String imageUrl;
    private MultipartFile image;

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MultipartFile getImage() {
        return image;
    }
}

