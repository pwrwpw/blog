package com.blog.pwrwpw.member.dto;

import jakarta.validation.constraints.NotBlank;

public class MemberRequest {

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    private MemberRequest() {

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
