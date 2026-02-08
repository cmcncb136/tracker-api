package com.kesi.tracker.notice.application.dto;

import com.kesi.tracker.notice.domain.NoticeType;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class NoticeResponse {
    private Long id;
    private Long gid;
    private NoticeType type;
    private UserProfileResponse authorProfile;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private UserProfileResponse modifiedAuthorProfile;
    List<String> attachmentFileUrls;
}
