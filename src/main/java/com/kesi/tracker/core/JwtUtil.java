package com.kesi.tracker.core;

import com.kesi.tracker.user.domain.Role;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {
    private final SecretKey secretKey;

    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Long getUserId(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJwt(jwt)
                .getPayload()
                .get("userId", Long.class);
    }

    public Role getRole(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJwt(jwt)
                .getPayload()
                .get("role", Role.class);
    }

    public Boolean isTokenExpired(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseClaimsJws(jwt)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(Long userId, Role role, Long expiredMs) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey)
                .compact();
    }
}