package com.kesi.tracker.track.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Track {
    private Long id;
    private Long gid;
    private Long hostId;

    private int capacity; //최대 수용인원
    private int followerCnt;

    private String title;
    private String introduce;
    private String description;
    private String place;
    private Long cost;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private Long createdBy;
    private Long modifiedBy;
}