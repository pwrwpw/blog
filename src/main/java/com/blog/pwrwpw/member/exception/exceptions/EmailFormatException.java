package com.blog.pwrwpw.member.exception.exceptions;

public class EmailFormatException extends RuntimeException{

    public EmailFormatException() {
        super("이메일 형식이 유효하지 않습니다.");
    }
}
