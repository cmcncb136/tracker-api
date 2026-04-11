package com.kesi.tracker.auth.application;

import com.kesi.tracker.auth.application.dto.TokenResponse;

public interface AuthService {
    TokenResponse refreshToken(String refreshToken);
}
