package com.kesi.tracker.track.domain;

import java.time.LocalDateTime;

public class Track {
    private Long id;
    private Long gid;
    private int hostId;

    private int capacity; //최대 수용인원
    private int followerCnt;
    private String imageUrl;

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