package com.blog.pwrwpw.member.exception.exceptions;

public class MemberNotEqualException extends RuntimeException{

    public MemberNotEqualException() {
        super("유저 정보가 일치하지 않습니다.");
    }
}
