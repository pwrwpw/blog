package com.blog.pwrwpw.member.service;

import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.dto.ChangePasswordRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    @Transactional
    public Member changeMembersPassword(final Member member, final ChangePasswordRequest changePasswordRequest) {
        member.validatePassword(changePasswordRequest.getCurrentPassword());
        validateNewPassword(changePasswordRequest);
        member.changePassword(changePasswordRequest.getNewPassword());

        return member;
    }

    public void validateNewPassword(final ChangePasswordRequest changePasswordRequest) {
        String newPassword = changePasswordRequest.getNewPassword();
        String newPasswordConfirm = changePasswordRequest.getNewPasswordConfirm();

        if (!newPassword.equals(newPasswordConfirm)) {
            throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다.");
        }
    }
}
