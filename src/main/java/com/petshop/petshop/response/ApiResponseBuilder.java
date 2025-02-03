package com.petshop.petshop.response;

import com.petshop.petshop.DTO.ApiResponseDTO;

import java.util.List;

public interface ApiResponseBuilder<T> {
    ApiResponseDTO<T> createSuccessResponse(T data);
    ApiResponseDTO<T> createErrorResponse(List<String> errors);
}
