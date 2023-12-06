package com.blog.pwrwpw.auth.service;

import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.dto.MemberRequest;

public interface AuthService {

    Member register(MemberRequest memberRequest);

    String login(MemberRequest memberRequest);
}
