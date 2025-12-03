package com.kesi.tracker.track.domain;

public class Track {
    private Long id;
    private Long gid;
    private int hostId;

    private int capacity; //최대 수용량
    private int followerCnt;
    private String imageUrl;

    private String title;
    private String description;
    private String place;
    private Long cost;
}