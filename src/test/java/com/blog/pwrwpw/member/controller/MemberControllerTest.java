package com.blog.pwrwpw.member.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.blog.pwrwpw.config.ArgumentResolverConfig;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.dto.ChangePasswordRequest;
import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidPasswordException;
import com.blog.pwrwpw.member.exception.exceptions.NewPasswordNotMatchException;
import com.blog.pwrwpw.member.service.MemberService;
import com.blog.pwrwpw.utils.GenerateToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MemberControllerTest {

    @MockBean
    private MemberService memberService;
    /*
    MockBean ArgumentResolverConfig 하는 이유
    ArgumentResolverConfig 에서 JwtLoginResolver 를 Bean 으로 등록했기 때문에
    제대로 된 토큰이 없으면 JwtLoginResolver 에서 예외를 발생시킨다.
    테스트용 토큰은 GenerateToken 에서 생성하고, 테스트용 토큰을 사용하려면
    JwtLoginResolver 에서 발생하는 예외를 막아야 한다.
     */
    @MockBean
    private ArgumentResolverConfig argumentResolverConfig;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @DisplayName("현재 비밀번호와 새 비밀번호, 새 비밀번호 확인이 일치하면 비밀번호를 변경한다.")
    @Test
    void changeMemberPassword() throws Exception {
        // given
        Member member = Member.of("q@r.com", "qw123");

        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("qw123", "q123", "q123");

        given(memberService.changeMembersPassword(any(Member.class), any(ChangePasswordRequest.class)))
                .willReturn(member);

        // when & then
        mockMvc.perform(patch("/member")
                        .header(HttpHeaders.AUTHORIZATION, GenerateToken.generateFakeToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(member.getEmail()));
    }

    @DisplayName("토큰이 없을 경우 401 에러를 반환한다.")
    @Test
    void failToChangePasswordUnauthorized() throws Exception {
        // given
        Member member = Member.of("q@r.com", "qw123");

        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("qw123", "q123", "q123");

        given(memberService.changeMembersPassword(any(Member.class), any(ChangePasswordRequest.class)))
                .willReturn(member);

        // when & then
        mockMvc.perform(patch("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("새 비밀번호, 새 비밀번호 확인이 일치하지 않으면 예외가 발생한다.")
    @Test
    void changeMemberPasswordWithNotMatchPassword() throws Exception {
        // given
        Member member = Member.of("q@r.com", "qw123");

        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("qw123", "q123", "q1234");

        given(memberService.changeMembersPassword(any(Member.class), any(ChangePasswordRequest.class)))
                .willThrow(NewPasswordNotMatchException.class);

        // when & then
        mockMvc.perform(patch("/member")
                        .header(HttpHeaders.AUTHORIZATION, GenerateToken.generateFakeToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호 변경 시 현재 비밀번호가 공백 일 경우 예외가 발생한다.")
    @Test
    void changeMemberPasswordWithEmptyCurrentPassword() throws Exception {
        // given
        Member member = Member.of("q@r.com", "qw123");

        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("", "q1234", "q1234");

        // when & then
        mockMvc.perform(patch("/member")
                        .header(HttpHeaders.AUTHORIZATION, GenerateToken.generateFakeToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호 변경 시 새 비밀번호가 공백 일 경우 예외가 발생한다.")
    @Test
    void changeMemberPasswordWithEmptyNewPassword() throws Exception {
        // given
        Member member = Member.of("q@r.com", "qw123");

        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("q1234", "", "q1234");

        // when & then
        mockMvc.perform(patch("/member")
                        .header(HttpHeaders.AUTHORIZATION, GenerateToken.generateFakeToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }

    @DisplayName("비밀번호 변경 시 새 비밀번호 확인이 공백 일 경우 예외가 발생한다.")
    @Test
    void changeMemberPasswordWithEmptyNewPasswordConfirm() throws Exception {
        // given
        Member member = Member.of("q@r.com", "qw123");

        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("q1234", "q1234", "");

        // when & then
        mockMvc.perform(patch("/member")
                        .header(HttpHeaders.AUTHORIZATION, GenerateToken.generateFakeToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }
    @DisplayName("비밀번호 변경 시 현재 비밀번호가 일치 하지 않을 경우 예외가 발생한다.")
    @Test
    void changeMemberPasswordWithNotMatchCurrentPassword() throws Exception {
        // given
        Member member = Member.of("q@r.com", "qw123");

        ChangePasswordRequest changePasswordRequest =
                new ChangePasswordRequest("q1234", "q1234", "q1234");

        given(memberService.changeMembersPassword(any(Member.class), any(ChangePasswordRequest.class)))
                    .willThrow(AuthInvalidPasswordException.class);
        // when & then
        mockMvc.perform(patch("/member")
                        .header(HttpHeaders.AUTHORIZATION, GenerateToken.generateFakeToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(changePasswordRequest)))
                .andExpect(status().isBadRequest());
    }
}
