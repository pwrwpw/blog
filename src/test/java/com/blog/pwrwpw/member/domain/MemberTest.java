package com.blog.pwrwpw.member.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidPasswordException;
import com.blog.pwrwpw.member.exception.exceptions.EmailFormatException;
import com.blog.pwrwpw.member.exception.exceptions.MemberPasswordEmptyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

public class MemberTest {

    @DisplayName("Member 객체 생성 테스트")
    @Test
    void createMember() {
        // when & then
        Assertions.assertDoesNotThrow(() -> Member.of("q1@r.com", "qw123"));
    }

    @DisplayName("이메일 형식이 아니면 예외가 발생한다.")
    @ValueSource(strings = {"q1r.com", "q1@rcom", "q1rcom", ""})
    @ParameterizedTest
    void throwExceptionIfEmailIsNotValid(String email) {
        // when & then
        assertThatThrownBy(() -> Member.of(email, "qw123"))
                .isInstanceOf(EmailFormatException.class);
    }

    @DisplayName("비밀번호는 공백 문자가 아니어야 한다.")
    @NullAndEmptySource
    @ParameterizedTest
    void throwExceptionIfPasswordIsBlank(String password) {
        // when & then
        assertThatThrownBy(() -> Member.of("q1@r.com", password))
                .isInstanceOf(MemberPasswordEmptyException.class);
    }

    @DisplayName("비밀번호가 member의 비밀번호와 일치하면 예외가 발생하지 않는다.")
    @Test
    void throwExceptionIfPasswordIsMatch() {
        // given
        Member member = Member.of("q1@r.com", "qw123");

        // when & then
        assertThatThrownBy(() -> member.validatePassword("password"))
                .isInstanceOf(AuthInvalidPasswordException.class);
    }
}
