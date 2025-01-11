package com.petshop.petshop.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class ProductRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.0", message = "Unit price must be greater than or equal to 0")
    private BigDecimal unitPrice;

    @NotNull(message = "Units in stock is required")
    @Min(value = 0, message = "Units in stock must be greater than or equal to 0")
    private int unitsInStock;

    private List<String> categoryIds;

    public String getName() {
        return name;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public List<String> getCategoryIds() {
        return categoryIds;
    }
}

