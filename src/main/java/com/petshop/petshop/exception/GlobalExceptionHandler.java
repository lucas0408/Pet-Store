package com.petshop.petshop.exception;

import com.petshop.petshop.DTO.ApiResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private ApiResponseBuilder<Object> responseBuilder;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse("Validation failed", errors));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleJsonErrors(HttpMessageNotReadableException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse(
                    "Invalid request format",
                    Collections.singletonList("The request body is invalid or malformed")
            ));
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String error = String.format("Parameter '%s' should be of type %s",
            ex.getName(), ex.getRequiredType().getSimpleName());

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse("Invalid parameter type", Collections.singletonList(error)));
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(responseBuilder.createErrorResponse(
                    "Resource not found",
                    Collections.singletonList(ex.getMessage())
            ));
  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleRuntime(RuntimeException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse(
                    "Processing error",
                    Collections.singletonList(ex.getMessage())
            ));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleGeneric(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(responseBuilder.createErrorResponse(
                    "Internal server error",
                    Collections.singletonList("An unexpected error occurred")
            ));
  }
}