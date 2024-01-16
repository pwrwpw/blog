package com.blog.pwrwpw.auth.exception;

import com.blog.pwrwpw.auth.exception.exceptions.BearerTokenNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(BearerTokenNotFoundException.class)
    public ResponseEntity<String> bearerTokenNotFoundException(BearerTokenNotFoundException exception) {
        return getNotFoundResponseEntity(exception.getMessage());
    }

    private ResponseEntity<String> getNotFoundResponseEntity(final String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

}
