package com.blog.pwrwpw.auth.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.blog.pwrwpw.auth.provider.JwtTokenProvider;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.domain.MemberRepository;
import com.blog.pwrwpw.member.dto.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("회원가입을 한다")
    @Test
    void register() {
        // given
        MemberRequest memberRequest = new MemberRequest("q@r.com", "qw123");
        // when
        Member member = authService.register(memberRequest);
        // then
        Member result = memberRepository.findById(member.getId()).get();
        assertThat(result.getEmail()).isEqualTo(memberRequest.getEmail());
    }

    @DisplayName("로그인을 한다")
    @Test
    void login() {
        // given
        MemberRequest memberRequest = new MemberRequest("q@r.com", "qw123");
//        authService.register(memberRequest);

        // when
        String token = authService.login(memberRequest);

        // then
        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo(memberRequest.getEmail());
    }
}
