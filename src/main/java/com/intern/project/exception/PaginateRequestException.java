package com.intern.project.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PaginateRequestException extends IllegalStateException{
    public PaginateRequestException(String message) {
        super(message);
    }
}
