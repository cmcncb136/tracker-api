package com.kesi.tracker.track.domain;

import com.kesi.tracker.group.domain.GroupTrackRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
@Builder
public class TrackMember {
    private Long id;
    private Long trackId;
    private Long uid;
    private LocalDateTime createdAt;
}