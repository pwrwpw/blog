package com.blog.pwrwpw.auth.login;

import com.blog.pwrwpw.auth.service.JwtAuthService;
import com.blog.pwrwpw.member.domain.Member;
import java.util.Objects;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class JwtLoginResolver implements HandlerMethodArgumentResolver {

    private static final String SEPARATOR = " ";
    private static final int BEARER_INDEX = 0;
    private static final int PAYLOAD_INDEX = 1;
    private static final String BEARER = "Bearer";

    private final JwtAuthService jwtAuthService;

    public JwtLoginResolver(final JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    public boolean supportsParameter(final MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JwtLogin.class);
    }

    @Override
    public Member resolveArgument(final MethodParameter parameter, final ModelAndViewContainer mavContainer,
                                  final NativeWebRequest webRequest, final WebDataBinderFactory binderFactory) {
        String authorization = webRequest.getHeader("Authorization");
        validateAuthorization(authorization);
        System.out.println("authorization = " + authorization);
        return jwtAuthService.findMemberByJwtPayload(getPayload(Objects.requireNonNull(authorization)));
    }

    private void validateAuthorization(final String authorization) {
        if (authorization == null || !isBearer(authorization)) {
            throw new IllegalArgumentException("올바른 인증 정보가 아닙니다.");
        }
    }

    private boolean isBearer(final String authorization) {
        return authorization.split(SEPARATOR)[BEARER_INDEX].equals(BEARER);
    }

    private String getPayload(final String authorization) {
        return authorization.split(SEPARATOR)[PAYLOAD_INDEX];
    }
}
