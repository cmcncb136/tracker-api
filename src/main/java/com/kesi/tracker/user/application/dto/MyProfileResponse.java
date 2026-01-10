package com.kesi.tracker.user.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MyProfileResponse {
    private String email;
    private String name;
    private String nickname;
    private String phone;
    private LocalDateTime birthday;
    private LocalDateTime createdAt;
    private List<String> profileImageUrls;
}
