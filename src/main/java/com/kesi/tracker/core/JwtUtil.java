package com.kesi.tracker.core;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.user.domain.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

@Component
public class JwtUtil {
    private static final String USER_ID_CLAIM = "userId";
    private static final String ROLE_CLAIM = "role";
    private static final String TYPE_CLAIM = "type";

    private static final String ROLE_VALUE_PREFIX = "ROLE_";

    private static final String ACCESS_VALUE = "access";
    private static final String REFRESH_VALUE = "refresh";

    private final SecretKey secretKey;


    public JwtUtil(@Value("${spring.jwt.secret}") String secret) {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public Long getUserId(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .get("userId", Long.class);
    }

    public Role getRole(String jwt) {
        String roleString = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .get("role", String.class);

        return Role.valueOf(roleString.replace("ROLE_", ""));    }

    public void validateToken(String jwt) {
        Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt);
    }

    public Boolean isTokenExpired(String jwt) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(jwt)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    private JwtBuilder defaultJwtBuilder(Long userId, Role role, Long expiredMs) {
        return Jwts.builder()
                .claim(USER_ID_CLAIM, userId)
                .claim(ROLE_CLAIM, ROLE_VALUE_PREFIX + role.name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(secretKey);
    }

    public String createAccessToken(Long userId, Role role) {
        return defaultJwtBuilder(userId, role, 60 * 60 * 1000L)
                .claim(TYPE_CLAIM, ACCESS_VALUE)
                .compact();
    }

    public String createRefreshToken(Long userId, Role role) {
        return defaultJwtBuilder(userId, role, 60 * 60 * 1000L * 24 * 7)
                .claim(TYPE_CLAIM, REFRESH_VALUE)
                .compact();
    }
}