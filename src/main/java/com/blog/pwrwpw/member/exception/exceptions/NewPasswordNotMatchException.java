package com.blog.pwrwpw.member.exception.exceptions;

public class NewPasswordNotMatchException extends RuntimeException{

    public NewPasswordNotMatchException() {
        super("새로운 비밀번호가 일치하지 않습니다.");
    }
}
