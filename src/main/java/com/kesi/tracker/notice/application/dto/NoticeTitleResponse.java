package com.kesi.tracker.notice.application.dto;

import com.kesi.tracker.user.application.dto.UserProfileResponse;

import java.time.LocalDateTime;

public class NoticeTitleResponse {
    private Long id;
    private Long gid;
    private UserProfileResponse authorProfile;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserProfileResponse modifiedAuthorProfile;
}
