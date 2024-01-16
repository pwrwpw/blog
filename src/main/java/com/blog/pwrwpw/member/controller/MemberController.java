package com.blog.pwrwpw.member.controller;

import com.blog.pwrwpw.auth.login.JwtLogin;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.dto.ChangePasswordRequest;
import com.blog.pwrwpw.member.dto.MemberRequest;
import com.blog.pwrwpw.member.dto.MemberResponse;
import com.blog.pwrwpw.member.service.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(final MemberService memberService) {
        this.memberService = memberService;
    }
    @PatchMapping
    public ResponseEntity<MemberResponse> changeMemberPassword(@JwtLogin final Member member, @RequestBody @Valid final ChangePasswordRequest changePasswordRequest) {
        Member loginMember = memberService.changeMembersPassword(member, changePasswordRequest);
        return ResponseEntity.ok(MemberResponse.from(loginMember));
    }
}
