package com.qlda.nhom6.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class BlogResourceNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public BlogResourceNotFoundException(String message) {
        super(message);
    }
}