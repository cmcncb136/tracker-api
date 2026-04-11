package com.kesi.tracker.auth.presentation;

import com.kesi.tracker.auth.application.AuthService;
import com.kesi.tracker.auth.application.dto.TokenResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/refresh")
    public void refreshToken(@RequestHeader("Refresh-Token") String refreshToken, HttpServletResponse response) {
        TokenResponse tokenResponse = authService.refreshToken(refreshToken);
        response.setHeader("Authorization", "Bearer " + tokenResponse.getAccessToken());
    }
}
