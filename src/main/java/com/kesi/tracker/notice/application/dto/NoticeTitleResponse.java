package com.kesi.tracker.notice.application.dto;

import com.kesi.tracker.notice.domain.NoticeType;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class NoticeTitleResponse {
    private Long id;
    private Long gid;
    private NoticeType type;
    private UserProfileResponse authorProfile;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserProfileResponse modifiedAuthorProfile;
}
