package com.kesi.tracker.track.application.query;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TrackWithGroupSearchCondition {
    private String title;
    private String introduction;
    private String groupName;
    private LocalDateTime assignmentStartAt;
    private LocalDateTime assignmentEndAt;
}
