package com.kesi.tracker.group.domain;

import java.time.LocalDateTime;

public class Group {
    private Long gid;
    private Long leaderUserId;
    private String name;
    private String introduce;
    private String description;
    private String profileUrl;

    private AccessType access;

    private Long createdBy;
    private LocalDateTime createdAt;
}
