package com.blog.pwrwpw.auth.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final long expirationTime;

    private final String key;

    public JwtTokenProvider(@Value("${jwt.secret}") final String secretKey,
                            @Value("${jwt.exp.access}") final long expirationTime) {
        this.key = secretKey;
        this.expirationTime = expirationTime;
    }

    public String create(final String id) {
        Claims claims = Jwts.claims();
        claims.put("id", id);
        return createAccessToken(claims);
    }

    private String createAccessToken(final Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issueDate())
                .setExpiration(calculateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    private Date issueDate() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
    }

    private Date calculateExpirationDate() {
        LocalDateTime now = LocalDateTime.now();
        return Date.from(now.plusHours(expirationTime).atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getPayload(final String token) {
        JwtParser parser = Jwts.parser().setSigningKey(key);
        Jws<Claims> claimsJws = parser.parseClaimsJws(token);
        Claims body = claimsJws.getBody();

        return body.get("id", String.class);
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT 토큰이 잘못되었습니다.");
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("토큰이 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("지원되지 않는 토큰 형식입니다.");
        } catch (SignatureException e) {
            throw new IllegalArgumentException("토큰 서명이 올바르지 않습니다.");
        }
    }
}
