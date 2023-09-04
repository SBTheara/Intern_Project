package com.example.mysmallproject.exception;
import jakarta.validation.ConstraintViolationException;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@RestControllerAdvice
@Data
public class GlobalExceptionHandler {
    public final static String NOT_FOUND = "Item not found !!!";
    public final static String ERROR = "Something went wrong !!!";
    public final static String ADDED = "Successfully added !!!";
    public final static String GET_USER = "Successfully found !!!";
    public final static String UPDATED = "Successfully updated !!!";
    public final static String DELETED ="Successfully deleted !!!";
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<?> handlerException (IllegalStateException exception){
        return ResponseEntity
                .badRequest()
                .body(exception.getMessage());
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handlerConstraintValidationException(ConstraintViolationException exception){
        return ResponseEntity
                .badRequest()
                .body(
                        exception.getConstraintViolations().stream().map(violation->violation.getPropertyPath()+" : "+violation.getMessage())
                );
    }
}
