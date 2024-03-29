package com.blog.pwrwpw.member.dto;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordRequest {

    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    private String currentPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    private String newPassword;

    @NotBlank(message = "새 비밀번호를 다시 입력해주세요.")
    private String newPasswordConfirm;

    private ChangePasswordRequest() {

    }

    public ChangePasswordRequest(final String currentPassword, final String newPassword,
                                 final String newPasswordConfirm) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.newPasswordConfirm = newPasswordConfirm;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getNewPasswordConfirm() {
        return newPasswordConfirm;
    }
}
