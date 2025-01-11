package com.petshop.petshop.exception;

import com.petshop.petshop.DTO.ApiResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultApiResponseBuilder<T> implements ApiResponseBuilder<T> {
    @Override
    public ApiResponseDTO<T> createSuccessResponse(T data, String message) {
        return new ApiResponseDTO<>(true, data, message, new ArrayList<>());
    }

    @Override
    public ApiResponseDTO<T> createErrorResponse(String message, List<String> errors) {
        return new ApiResponseDTO<>(false, null, message, errors);
    }
}
