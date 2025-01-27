package com.petshop.petshop.exception;

import com.petshop.petshop.DTO.ApiResponseDTO;
import com.petshop.petshop.response.ApiResponseBuilder;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

  @Autowired
  private ApiResponseBuilder<Object> responseBuilder;

  //lida com erros de validação de campo ex: @NotNull, @Size, Etc.
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> String.format("%s: %s", error.getField(), error.getDefaultMessage()))
            .collect(Collectors.toList());

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse(errors));
  }

  //lida com erros de JSON mal formatado
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleJsonErrors(HttpMessageNotReadableException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse(
                    Collections.singletonList("The request body is invalid or malformed")
            ));
  }

  //lida com erro de tipo em parametro URL, Ex: quando se espera um número mas recebe uma string.
  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    String error = String.format("Parameter '%s' should be of type %s",
            ex.getName(), ex.getRequiredType().getSimpleName());

    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse(Collections.singletonList(error)));
  }

  //lida com erro de recursos não encontrados
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
    return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(responseBuilder.createErrorResponse(
                    Collections.singletonList(ex.getMessage())
            ));
  }

  //lida com erros de validação personalizado
  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleValidationException(ValidationException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse(
                    Collections.singletonList(ex.getMessage())
            ));
  }

  //lida com erro de IOException, quando algum sinal de entrada/saída falha ou é interrompido
  @ExceptionHandler(IOException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleIOIOException(IOException ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(responseBuilder.createErrorResponse(
                    Collections.singletonList(ex.getMessage())
            ));
  }

  //lida com erro de RuntimeException genérica
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleRuntime(RuntimeException ex) {
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(responseBuilder.createErrorResponse(
                    Collections.singletonList(ex.getMessage())
            ));
  }

  //lida com erro de Exception genérica
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiResponseDTO<Object>> handleGeneric(Exception ex) {
    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(responseBuilder.createErrorResponse(
                    Collections.singletonList("An unexpected error occurred")
            ));
  }
}