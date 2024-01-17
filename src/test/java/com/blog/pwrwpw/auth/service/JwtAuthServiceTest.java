package com.blog.pwrwpw.auth.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.blog.pwrwpw.auth.provider.JwtTokenProvider;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.domain.MemberRepository;
import com.blog.pwrwpw.member.dto.MemberRequest;
import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidEmailException;
import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidPasswordException;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotEqualException;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JwtAuthServiceTest {
    @InjectMocks
    private JwtAuthService jwtAuthService;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @DisplayName("로그인 시 일치하지 않는 비밀번호를 입력하면 예외가 발생한다.")
    @Test
    void loginWithNotMatchPassword() {
        // given
        Member member = Member.of("qwww@r.com", "qw123");
        MemberRequest memberRequest = new MemberRequest("qwww@r.com", "qw23");
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

        // when & then
        assertThatThrownBy(() -> jwtAuthService.login(memberRequest))
            .isInstanceOf(AuthInvalidPasswordException.class);
    }

    @DisplayName("로그인 시 일치하지 않는 이메일을 입력하면 예외가 발생한다.")
    @Test
    void loginWithNotMatchEmail() {
        // given
        Member member = Member.of("qwww@r.com", "qw123");
        MemberRequest memberRequest = new MemberRequest("qb@r.com", "qw123");
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));
        // when & then
        assertThatThrownBy(() -> jwtAuthService.login(memberRequest))
                .isInstanceOf(AuthInvalidEmailException.class);
    }

    @DisplayName("로그인 시 존재하지 않는 이메일을 입력하면 예외가 발생한다.")
    @Test
    void loginWithNotExistEmail() {
        // given
        MemberRequest memberRequest = new MemberRequest("qw122@r.com", "qw123");
        // when & then
        assertThatThrownBy(() -> jwtAuthService.login(memberRequest))
                .isInstanceOf(MemberNotFoundException.class);
    }

    @DisplayName("payload에서 id를 가져올 떄 존재 하지 않으면 예외가 발생한다.")
    @Test
    void getIdFromPayloadWithNotExistId() {
        // given
        String payload = "payload";
        // when & then
        assertThatThrownBy(() -> jwtAuthService.findMemberByJwtPayload(payload))
                .isInstanceOf(MemberNotEqualException.class);
    }
}