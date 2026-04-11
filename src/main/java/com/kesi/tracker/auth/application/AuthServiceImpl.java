package com.kesi.tracker.auth.application;

import com.kesi.tracker.auth.application.dto.TokenResponse;
import com.kesi.tracker.core.JwtUtil;
import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import com.kesi.tracker.user.domain.Role;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final JwtUtil jwtUtil;

    @Override
    public TokenResponse refreshToken(String refreshToken) {
        try {
            jwtUtil.validateToken(refreshToken);
        }catch (SecurityException | MalformedJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_SIGNATURE_INVALID);
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new BusinessException(ErrorCode.TOKEN_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.TOKEN_INVALID);
        }

        Long userId = jwtUtil.getUserId(refreshToken);
        Role role = jwtUtil.getRole(refreshToken);

        return TokenResponse.builder()
                .accessToken(jwtUtil.createAccessToken(userId, role))
                .build();
    }
}
