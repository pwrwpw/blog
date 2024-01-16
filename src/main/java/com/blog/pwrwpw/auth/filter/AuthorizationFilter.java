package com.blog.pwrwpw.auth.filter;

import com.blog.pwrwpw.auth.provider.JwtTokenProvider;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthorizationFilter implements Filter {

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_ = "Bearer ";

    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();

        if (isLoginURI(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader(AUTHORIZATION);

        if (token == null || !token.startsWith(BEARER_)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 존재하지 않습니다.");
            return;
        }

        token = token.replace(BEARER_, "");
        if(!(jwtTokenProvider.validateToken(token))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean isLoginURI(final String requestURI) {
        return requestURI.startsWith("/auth");
    }
}