package com.petshop.petshop.response;

import com.petshop.petshop.DTO.ApiResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultApiResponseBuilder<T> implements ApiResponseBuilder<T> {
    @Override
    public ApiResponseDTO<T> createSuccessResponse(T data) {
        return new ApiResponseDTO<>(true, data, new ArrayList<>());
    }

    @Override
    public ApiResponseDTO<T> createErrorResponse(List<String> errors) {
        return new ApiResponseDTO<>(false, null, errors);
    }
}
