package com.kesi.tracker.group.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Group {
    private Long gid;
    private String name;
    private String introduce;
    private String description;
    private String profileUrl;


    private AccessType access;
    private Long createdBy;
    private LocalDateTime createdAt;
}
