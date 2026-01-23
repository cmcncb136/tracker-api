package com.kesi.tracker.track.application.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TrackAssignmentResponse {
    private Long id;
    private Long trackId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private Integer capacity;
    private Integer maxCapacity;
}
