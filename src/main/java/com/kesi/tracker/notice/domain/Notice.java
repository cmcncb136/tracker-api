package com.kesi.tracker.notice.domain;

import java.time.LocalDateTime;

public class Notice {
    private Long id;
    private Long gid;
    private Long authorId;
    private NoticeType type;
    private String title;
    private String content;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createdBy;
    private Long modifiedBy;
}
