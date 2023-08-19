package com.example.mysmallproject.advice;

import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String,String> handlerForHttpException(HttpMessageNotReadableException ex){
        Map<String,String> error = new HashMap<>();
        error.put(ex.getCause().toString(),ex.getMessage().toString());
        return error;
    }
}
