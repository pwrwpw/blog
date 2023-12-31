package com.blog.pwrwpw.member.dto;

import com.blog.pwrwpw.member.domain.Member;

public class MemberResponse {

    private Long id;
    private String email;

    private MemberResponse(final Long id, final String email) {
        this.id = id;
        this.email = email;
    }

    public static MemberResponse from(final Member member) {
        return new MemberResponse(member.getId(), member.getEmail());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
