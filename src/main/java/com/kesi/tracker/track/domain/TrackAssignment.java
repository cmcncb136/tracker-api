package com.kesi.tracker.track.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TrackAssignment {
    private final Long id;
    private final Long trackId;
    private final LocalDateTime startAt;
    private final LocalDateTime endAt;

    private final Integer capacity;
    private final Integer maxCapacity;

    public boolean isWithinPeriod(LocalDateTime target) {
        return !target.isBefore(startAt) && !target.isAfter(endAt);
    }
}
