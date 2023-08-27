package com.example.mysmallproject.advice;

import com.example.mysmallproject.Exception.ApiRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
@RestControllerAdvice
public class applicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String> handlerInvalidException(MethodArgumentNotValidException ex){
        Map<String,String> errorMap= new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<?> handlerForHttpException(ApiRequestException ex){
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
