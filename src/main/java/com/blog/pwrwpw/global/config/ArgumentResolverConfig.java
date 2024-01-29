package com.blog.pwrwpw.global.config;

import com.blog.pwrwpw.auth.login.JwtLoginResolver;
import com.blog.pwrwpw.auth.service.JwtAuthService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ArgumentResolverConfig implements WebMvcConfigurer {

    private final JwtAuthService jwtAuthService;

    public ArgumentResolverConfig(JwtAuthService jwtAuthService) {
        this.jwtAuthService = jwtAuthService;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(jwtLoginResolver());
    }

    @Bean
    public JwtLoginResolver jwtLoginResolver() {
        return new JwtLoginResolver(jwtAuthService);
    }
}
