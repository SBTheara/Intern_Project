package com.intern.project.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
@RestControllerAdvice
@Data
public class GlobalExceptionHandler {
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<?> handlerException(IllegalStateException exception) {
    return ResponseEntity.badRequest().body(new ApiError(HttpStatus.BAD_REQUEST, exception.getLocalizedMessage(),exception.getCause()));
  }
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handlerConstraintValidationException(
      ConstraintViolationException exception) {
    Map<String,String> errors = new HashMap<>();
    for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
      errors.put(violation.getPropertyPath().toString(),violation.getMessage());
    }
    return ResponseEntity.badRequest()
        .body(new ApiError(HttpStatus.BAD_REQUEST,exception.getLocalizedMessage(),errors));
  }
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ApiError> productNotFoundException (NoSuchElementException noSuchElementException){
    ApiError error = new ApiError(
            HttpStatus.NOT_FOUND,
            noSuchElementException.getLocalizedMessage(),
            noSuchElementException.getCause()
    );
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
}
