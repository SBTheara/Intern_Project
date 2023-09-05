package com.example.mysmallproject.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Data
public class GlobalExceptionHandler {
  public static final String NOT_FOUND = "Item not found !!!";
  public static final String ERROR = "Something went wrong !!!";
  public static final String ADDED = "Successfully added !!!";
  public static final String GET_USER = "Successfully found !!!";
  public static final String UPDATED = "Successfully updated !!!";
  public static final String DELETED = "Successfully deleted !!!";

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<?> handlerException(IllegalStateException exception) {
    return ResponseEntity.badRequest().build();
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<?> handlerConstraintValidationException(
      ConstraintViolationException exception) {
    return ResponseEntity.badRequest()
        .body(
            exception.getConstraintViolations().stream()
                .map(violation -> violation.getPropertyPath() + " : " + violation.getMessage()));
  }
  @ExceptionHandler(ProductNotFoundException.class)
  public ResponseEntity<ApiError> productNotFoundException ( ProductNotFoundException productNotFoundException){
    ApiError error = new ApiError(
            HttpStatus.NOT_FOUND,
            productNotFoundException.getMessage(),
            productNotFoundException.getCause()
    );
    return new ResponseEntity<>(error,HttpStatus.NOT_FOUND);
  }

}
