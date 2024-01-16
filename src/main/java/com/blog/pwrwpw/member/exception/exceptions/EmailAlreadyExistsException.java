package com.blog.pwrwpw.member.exception.exceptions;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("이미 존재하는 이메일입니다.");
    }
}
