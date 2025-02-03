package com.petshop.petshop.DTO;

import com.petshop.petshop.model.Product;
import com.petshop.petshop.model.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class ProductDTO {
    private String id;
    private String name;
    private BigDecimal unitPrice;
    private Integer unitsInStock;
    private String imageUrl;
    private MultipartFile image;
    private Set<Category> categories = new HashSet<>();

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Integer getUnitsInStock() {
        return unitsInStock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public MultipartFile getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", unitsInStock=" + unitsInStock +
                ", imageUrl='" + imageUrl + '\'' +
                ", image=" + image +
                ", categories=" + categories +
                '}';
    }
}
