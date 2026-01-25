package com.kesi.tracker.track.application.query;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TrackSearchCondition{
    private String title;
    private String introduction;
    private LocalDateTime assignmentStartAt;
    private LocalDateTime assignmentEndAt;
}
