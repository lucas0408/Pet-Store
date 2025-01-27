package com.petshop.petshop.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record ApiResponseDTO<T>(boolean success, T data, List<String> errors) {
    public ApiResponseDTO(boolean success, T data, List<String> errors) {
        this.success = success;
        this.data = data;
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }
}
