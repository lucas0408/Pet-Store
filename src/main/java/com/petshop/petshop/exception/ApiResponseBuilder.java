package com.petshop.petshop.exception;

import com.petshop.petshop.DTO.ApiResponseDTO;

import java.util.List;

public interface ApiResponseBuilder<T> {
    ApiResponseDTO<T> createSuccessResponse(T data, String message);
    ApiResponseDTO<T> createErrorResponse(String message, List<String> errors);
}
