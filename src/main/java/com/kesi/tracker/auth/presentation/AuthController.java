package com.kesi.tracker.auth.presentation;

import com.kesi.tracker.auth.application.AuthService;
import com.kesi.tracker.auth.application.dto.TokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    @Operation(
            summary = "Access Token 재발급",
            description = "Refresh Token을 헤더로 전달받아 새로운 Access Token을 발급하고 응답 헤더에 담아 반환합니다."
    )
    public void refreshToken(
            @Parameter(
                    name = "Refresh-Token",
                    description = "발급받았던 리프레시 토큰 (Bearer 접두어 제외)",
                    required = true,
                    in = ParameterIn.HEADER,
                    example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            )
            @RequestHeader("Refresh-Token") String refreshToken, HttpServletResponse response) {
        TokenResponse tokenResponse = authService.refreshToken(refreshToken);
        response.setHeader("Authorization", "Bearer " + tokenResponse.getAccessToken());
    }
}
