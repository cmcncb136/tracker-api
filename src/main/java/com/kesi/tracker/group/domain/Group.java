package com.kesi.tracker.group.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Group {
    private Long gid;
    private String name;
    private String introduce;
    private String description;

    private AccessType access;
    private Long createdBy;
    private LocalDateTime createdAt;
}
