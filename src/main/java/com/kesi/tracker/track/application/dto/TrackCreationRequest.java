package com.kesi.tracker.track.application.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackCreationRequest {
    private Long gid;

    private int capacity;

    private String title;
    private String description;
    private String introduction;
    private String place;
    private Long cost;

    private LocalDateTime assignmentStartAt;
    private LocalDateTime assignmentEndAt;
}
