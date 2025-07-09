package com.petshop.petshop.DTO;

import com.petshop.petshop.model.Product;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record ProductResponseDTO(
        String id,
        String name,
        BigDecimal unitPrice,
        int unitsInStock,
        String imageUrl,
        Set<String> categories
) {
    public ProductResponseDTO(Product product) {
        this(
                null,
                product.getName(),
                product.getUnitPrice(),
                product.getUnitsInStock(),
                product.getImageUrl(),
                product.getCategories() != null ? product.getCategories().stream().map(category -> category.getId()).collect(Collectors.toSet()) : new HashSet<>()
        );
    }
}
