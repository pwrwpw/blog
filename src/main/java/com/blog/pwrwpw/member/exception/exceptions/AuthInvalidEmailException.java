package com.blog.pwrwpw.member.exception.exceptions;

public class AuthInvalidEmailException extends RuntimeException{

    public AuthInvalidEmailException() {
        super("요청하신 이메일이 유효하지 않습니다.");
    }
}
