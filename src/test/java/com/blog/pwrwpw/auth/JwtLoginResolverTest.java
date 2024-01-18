package com.blog.pwrwpw.auth.login;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.blog.pwrwpw.auth.exception.exceptions.BearerTokenNotFoundException;
import com.blog.pwrwpw.auth.service.JwtAuthService;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.dto.MemberRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class JwtLoginResolverTest {

    @Autowired
    private JwtLoginResolver jwtLoginResolver;

    @Autowired
    private JwtAuthService jwtAuthService;

    private final MethodParameter methodParameter = mock(MethodParameter.class);
    private final ModelAndViewContainer modelAndViewContainer = mock(ModelAndViewContainer.class);
    private final NativeWebRequest webRequest = mock(NativeWebRequest.class);
    private final WebDataBinderFactory webDataBinderFactory = mock(WebDataBinderFactory.class);

    @DisplayName("올바른 토큰으로 접근 시, 회원 정보 반환")
    @Test
    void return_access_member() throws Exception {
        // given
        Member member = mock(Member.class);
        given(member.getId()).willReturn(2L);
        given(member.getEmail()).willReturn("q@r.com");
        given(member.getPassword()).willReturn("qw123");

//        jwtAuthService.register(new MemberRequest(member.getEmail(), member.getPassword()));
        String token = jwtAuthService.login(new MemberRequest(member.getEmail(), member.getPassword()));
        String header = "Bearer " + token;
        given(webRequest.getHeader("Authorization")).willReturn(header);
        // when
        Member result = jwtLoginResolver.resolveArgument(methodParameter, modelAndViewContainer, webRequest, webDataBinderFactory);

        // then
        assertThat(result).usingRecursiveComparison().isEqualTo(member);
    }

    @DisplayName("잘못된 토큰으로 접근 시, 예외 발생")
    @Test
    void throw_exception_when_invalid_token() {

        assertThatThrownBy(() -> {
            jwtLoginResolver.resolveArgument(methodParameter, modelAndViewContainer, webRequest, webDataBinderFactory);
        }).isInstanceOf(BearerTokenNotFoundException.class);
    }
}