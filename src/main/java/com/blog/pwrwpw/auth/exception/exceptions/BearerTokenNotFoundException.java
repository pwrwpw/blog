package com.blog.pwrwpw.auth.exception.exceptions;

public class BearerTokenNotFoundException extends RuntimeException{

    public BearerTokenNotFoundException() {
        super("Bearer 토큰을 찾을 수 없습니다.");
    }
}
