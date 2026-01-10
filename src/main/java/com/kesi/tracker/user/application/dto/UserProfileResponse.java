package com.kesi.tracker.user.application.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserProfileResponse {
    private String email;
    private String nickname;
    private List<String> profileImageUrls;
}
