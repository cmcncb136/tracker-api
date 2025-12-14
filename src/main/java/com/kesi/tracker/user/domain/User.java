package com.kesi.tracker.user.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private String profileUrl;
    private LocalDateTime birthday;
    private LocalDateTime createdAt;
    private Role role;
}
