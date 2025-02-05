package com.petshop.petshop.model;

import com.petshop.petshop.DTO.ProductDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @NotBlank(message = "O nome do produto é obrigatório")
    @Column(name = "name", unique = true)
    private String name;

    @NotNull(message = "Unit price is required")
    @Min(value = 0, message = "O preço unitário deve ser maior ou igual a 0")
    @Column(name = "unitPrice")
    private BigDecimal unitPrice;

    @NotNull(message = "Units in stock is required")
    @Min(value = 0, message = "A quantidade em estoque deve ser maior ou igual a 0")
    @Column(name = "unitsInStock")
    private int unitsInStock;

    @Column(name = "imageUrl")
    private String imageUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories = new HashSet<>();

    public Product(ProductDTO product) {
        BeanUtils.copyProperties(product, this);
    }

    public Product() {
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
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

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", unitPrice=" + unitPrice +
                ", unitsInStock=" + unitsInStock +
                ", imageUrl='" + imageUrl + '\'' +
                ", categories=" + categories +
                '}';
    }
}
