package com.petshop.petshop.model;

import com.petshop.petshop.DTO.CategoryDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.BeanUtils;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @NotBlank(message = "O nome é obrigatorio")
    @Size(max = 70, message = "O nome pode ter no máximo 70 caracteres")
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "imageUrl")
    private String imageUrl;

    public Category(CategoryDTO category) {
        BeanUtils.copyProperties(category, this);
    }

    public Category(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
