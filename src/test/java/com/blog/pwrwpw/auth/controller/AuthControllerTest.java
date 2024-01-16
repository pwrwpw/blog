package com.blog.pwrwpw.auth.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.blog.pwrwpw.auth.dto.TokenResponse;
import com.blog.pwrwpw.auth.service.JwtAuthService;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.dto.MemberRequest;
import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidPasswordException;
import com.blog.pwrwpw.member.exception.exceptions.EmailAlreadyExistsException;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotEqualException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AuthControllerTest {

    @MockBean
    private JwtAuthService jwtAuthService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private Member member;

    @DisplayName("회원가입을 진행한다.")
    @Test
    void signUp() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("q@r.com", "qw123");
        given(jwtAuthService.register(any(MemberRequest.class))).willReturn(member);
        given(member.getId()).willReturn(1L);
        given(member.getEmail()).willReturn(memberRequest.getEmail());

        // when & then
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value(memberRequest.getEmail()));
    }

    @DisplayName("로그인을 진행한다.")
    @Test
    void signIn() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("q@r.com", "qw123");
        TokenResponse tokenResponse = TokenResponse.from("accesstoken");

        given(jwtAuthService.login(any(MemberRequest.class))).willReturn("accesstoken");

        // when & then
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken").value(tokenResponse.getAccessToken()));
    }

    @DisplayName("이미 존재하는 회원이면 회원가입을 진행하지 않는다.")
    @Test
    void signUpWithAlreadyExistsMember() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("q@r.com", "qw123");

        given(jwtAuthService.register(any(MemberRequest.class))).willThrow(
                new EmailAlreadyExistsException());

        // when & then
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("공백의 이메일로 회원가입을 진행하면 예외를 발생시킨다.")
    @Test
    void signUpWithEmptyEmail() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("", "qw123");

        // when & then
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("공백의 비밀번호로 회원가입을 진행하면 예외를 발생시킨다.")
    @Test
    void signUpWithEmptyPassword() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("asd@nav.com", "");

        // when & then
        mockMvc.perform(post("/auth/sign-up")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("존재하지 않는 이메일로 로그인을 진행하면 예외를 발생시킨다.")
    @Test
    void signInWithNotExistsEmail() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("없음@a.com", "qw123");

        given(jwtAuthService.login(any(MemberRequest.class))).willThrow(
                new MemberNotEqualException());

        // when & then
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest)))
                .andExpect(status().isForbidden());
    }

    @DisplayName("잘못된 비밀번호 로그인을 진행하면 예외를 발생시킨다.")
    @Test
    void signInWithInvalidPassword() throws Exception {
        // given
        MemberRequest memberRequest = new MemberRequest("test@test.com", "qw123");

        given(jwtAuthService.login(any(MemberRequest.class))).willThrow(
                new AuthInvalidPasswordException());

        // when & then
        mockMvc.perform(post("/auth/sign-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(memberRequest)))
                .andExpect(status().isBadRequest());
    }
}

