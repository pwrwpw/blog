package com.blog.pwrwpw.member.service;

import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.dto.ChangePasswordRequest;
import com.blog.pwrwpw.member.exception.exceptions.NewPasswordNotMatchException;
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
            throw new NewPasswordNotMatchException();
        }
    }
}
