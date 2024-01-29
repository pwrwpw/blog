package com.blog.pwrwpw.board.exception;

import com.blog.pwrwpw.board.exception.exceptions.BoardContentEmptyException;
import com.blog.pwrwpw.board.exception.exceptions.BoardNotFoundException;
import com.blog.pwrwpw.board.exception.exceptions.BoardTitleEmptyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BoardExceptionHandler {

    @ExceptionHandler(BoardTitleEmptyException.class)
    public ResponseEntity<String> boardTitleEmptyException(BoardTitleEmptyException e) {
        return getBadRequestException(e.getMessage());
    }

    @ExceptionHandler(BoardContentEmptyException.class)
    public ResponseEntity<String> boardContentEmptyException(BoardContentEmptyException e) {
        return getBadRequestException(e.getMessage());
    }

    @ExceptionHandler(BoardNotFoundException.class)
    public ResponseEntity<String> boardNotFoundException(BoardNotFoundException e) {
        return getNotFoundException(e.getMessage());
    }

    private ResponseEntity<String> getBadRequestException(String message) {
        return ResponseEntity.badRequest().body(message);
    }

    private ResponseEntity<String> getNotFoundException(String message) {
        return ResponseEntity.notFound().build();
    }
}
