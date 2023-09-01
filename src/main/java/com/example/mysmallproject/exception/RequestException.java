package com.example.mysmallproject.exception;

public class RequestException extends RuntimeException{
    public RequestException(String message) {
        super(message);
    }
}
