package com.blog.pwrwpw.auth.filter;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AuthorizationFilter implements Filter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_ = "Bearer";

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/auth")) {
            chain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(AUTHORIZATION);
        System.out.println("token = " + token);
        if (token == null || !token.startsWith(BEARER_)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 존재하지 않습니다.");
            return;
        }

        // 토큰이 유효하지 않을 때
        if (!isValidToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.");
            return;
        }
    }
    private boolean isValidToken(final String token) {
        return true;
    }
}
