package com.kesi.tracker.track.application.dto;

import com.kesi.tracker.user.application.dto.UserProfileResponse;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class TrackResponse {
    private Long id;
    private Long gid;
    private UserProfileResponse hostProfile;

    private int capacity;
    private int followersCount;

    private String title;
    private String introduction;
    private String description;
    private String place;
    private Long cost;

    private LocalDateTime assignmentStartAt;
    private LocalDateTime assignmentEndAt;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<String> profileUrls;
}
