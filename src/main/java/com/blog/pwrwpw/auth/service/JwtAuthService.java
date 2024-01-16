package com.blog.pwrwpw.auth.service;

import com.blog.pwrwpw.auth.provider.JwtTokenProvider;
import com.blog.pwrwpw.member.domain.Member;
import com.blog.pwrwpw.member.domain.MemberRepository;
import com.blog.pwrwpw.member.dto.MemberRequest;
import com.blog.pwrwpw.member.exception.exceptions.EmailAlreadyExistsException;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotEqualException;
import com.blog.pwrwpw.member.exception.exceptions.MemberNotFoundException;
import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class JwtAuthService implements AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Transactional
    public Member register(MemberRequest memberRequest) {
        validateEmail(memberRequest.getEmail());
        return memberRepository.save(Member.of(memberRequest.getEmail(), memberRequest.getPassword()));
    }

    @Transactional
    public String login(MemberRequest memberRequest) {
        registerIfNotExists(memberRequest);

        Member member = memberRepository.findByEmail(memberRequest.getEmail())
                .orElseThrow(MemberNotEqualException::new);
        member.validateEmail(memberRequest.getEmail());
        member.validatePassword(memberRequest.getPassword());
        return jwtTokenProvider.create(member.getEmail());
    }

    private void registerIfNotExists(final MemberRequest memberRequest) {
        if (!memberRepository.existsByEmail(memberRequest.getEmail())) {
            memberRepository.saveAndFlush(Member.of(memberRequest.getEmail(), memberRequest.getPassword()));
        }
    }

    @Transactional(readOnly = true)
    public Member findMemberByJwtPayload(final String payload) {
        String jwtPayloadEmail = jwtTokenProvider.getPayload(payload);
        return memberRepository.findByEmail(jwtPayloadEmail)
                .orElseThrow(MemberNotFoundException::new);
    }

    private void validateEmail(final String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException();
        }
    }
}
