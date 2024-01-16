package com.blog.pwrwpw.auth.controller;

import com.blog.pwrwpw.auth.dto.TokenResponse;
import com.blog.pwrwpw.auth.service.AuthService;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.dto.MemberRequest;
import com.blog.pwrwpw.member.dto.MemberResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponse> signup(@RequestBody @Valid final MemberRequest memberRequest) {
        Member registeredMember = authService.register(memberRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(MemberResponse.from(registeredMember));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<TokenResponse> signin(@RequestBody @Valid final MemberRequest memberRequest) {
        String accessToken = authService.login(memberRequest);
        return ResponseEntity.ok(TokenResponse.from(accessToken));
    }
}
