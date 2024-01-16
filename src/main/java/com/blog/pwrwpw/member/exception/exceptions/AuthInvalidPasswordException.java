package com.blog.pwrwpw.member.exception.exceptions;

public class AuthInvalidPasswordException extends RuntimeException{

    public AuthInvalidPasswordException() {
        super("요청하신 비밀번호가 유효하지 않습니다.");
    }
}
