package com.blog.pwrwpw.member.exception;

import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidEmailException;
import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidPasswordException;
import com.blog.pwrwpw.member.exception.exceptions.EmailAlreadyExistsException;
import com.blog.pwrwpw.member.exception.exceptions.EmailFormatException;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotEqualException;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotFoundException;
import com.blog.pwrwpw.member.exception.exceptions.MemberPasswordEmptyException;
import com.blog.pwrwpw.member.exception.exceptions.NewPasswordNotMatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(AuthInvalidEmailException.class)
    public ResponseEntity<String> authInvalidEmailException(AuthInvalidEmailException exception) {
        return getBadRequestResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(AuthInvalidPasswordException.class)
    public ResponseEntity<String> authInvalidPasswordException(AuthInvalidPasswordException exception) {
        return getBadRequestResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<String> emailAlreadyExistsException(EmailAlreadyExistsException exception) {
        return getForbiddenResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(EmailFormatException.class)
    public ResponseEntity<String> emailNotFoundException(EmailFormatException exception) {
        return getBadRequestResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(MemberNotEqualException.class)
    public ResponseEntity<String> memberNotEqualException(MemberNotEqualException exception) {
        return getForbiddenResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(MemberNotFoundException.class)
    public ResponseEntity<String> memberNotFoundException(MemberNotFoundException exception) {
        return getNotFoundResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(MemberPasswordEmptyException.class)
    public ResponseEntity<String> memberPasswordEmptyException(MemberPasswordEmptyException exception) {
        return getBadRequestResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(NewPasswordNotMatchException.class)
    public ResponseEntity<String> newPasswordNotMatchException(NewPasswordNotMatchException exception) {
        return getBadRequestResponseEntity(exception.getMessage());
    }

    private ResponseEntity<String> getNotFoundResponseEntity(final String message) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(message);
    }

    private ResponseEntity<String> getBadRequestResponseEntity(final String message) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    private ResponseEntity<String> getForbiddenResponseEntity(final String message) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(message);
    }

}
