package com.petshop.petshop.DTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ApiResponseDTO<T> {
    private final boolean success;
    private final T data;
    private final String message;
    private final List<String> errors;

    public ApiResponseDTO(boolean success, T data, String message, List<String> errors) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.errors = Collections.unmodifiableList(new ArrayList<>(errors));
    }

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public String getMessage() { return message; }
    public List<String> getErrors() { return errors; }
}
