package com.blog.pwrwpw.member.exception.exceptions;

public class MemberPasswordEmptyException extends RuntimeException{

    public MemberPasswordEmptyException() {
        super("비밀번호가 비어있습니다.");
    }
}
