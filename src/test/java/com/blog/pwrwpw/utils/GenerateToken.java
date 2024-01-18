package com.blog.pwrwpw.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class GenerateToken {

    private static final String BEARER_ = "Bearer ";

    public static String generateFakeToken() {
        return BEARER_ + "fakeToken";
    }
}
