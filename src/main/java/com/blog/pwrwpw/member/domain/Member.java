package com.blog.pwrwpw.member.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.regex.Pattern;

@Entity
@Table(name = "MEMBER")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 200)
    private String email;

    @Column(nullable = false)
    private String password;

    protected Member() {
    }

    private Member(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static Member of(final String email, final String password) {
        validateCreateMember(email, password);
        return new Member(null, email, password);
    }

    private static void validateCreateMember(final String email, final String password) {
        validateEmail(email);
        validatePassword(password);
    }

    private static boolean isEmailFormat(final String email) {
        return Pattern.matches("^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$", email);
    }

    private static void validateEmail(final String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("이메일은 필수 입력값입니다.");
        }
        if (!isEmailFormat(email)) {
            throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다.");
        }
    }

    private static void validatePassword(final String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("비밀번호는 필수 입력값입니다.");
        }
    }

    public void changePassword(final String password) {
        validatePassword(password);
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Member)) {
            return false;
        }
        final Member member = (Member) o;

        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}