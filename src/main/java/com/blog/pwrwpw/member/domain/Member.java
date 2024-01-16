package com.blog.pwrwpw.member.domain;

import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidEmailException;
import com.blog.pwrwpw.member.exception.exceptions.AuthInvalidPasswordException;
import com.blog.pwrwpw.member.exception.exceptions.EmailFormatException;
import com.blog.pwrwpw.member.exception.exceptions.MemberPasswordEmptyException;
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
        if (!isEmailFormat(email)) {
            throw new EmailFormatException();
        }

        if (isEmpty(password)) {
            throw new MemberPasswordEmptyException();
        }
    }

    private static boolean isEmailFormat(final String email) {
        return Pattern.matches("^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$", email);
    }

    private static boolean isEmpty(final String password) {
        return password == null || password.isBlank();
    }

    public void validatePassword(final String password) {
        if (!this.password.equals(password)) {
            throw new AuthInvalidPasswordException();
        }
    }

    public void validateEmail(final String email) {
        if (!this.email.equals(email)) {
            throw new AuthInvalidEmailException();
        }
    }

    public void changePassword(final String newPassword) {
        this.password = newPassword;
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
        Member member = (Member) o;

        return Objects.equals(id, member.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}