package com.kesi.tracker.track.domain.event;

import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.Builder;

import java.util.List;

@Builder
public record TrackAppliedEvent (
    Long groupId,
    Long trackAssignmentId,
    Long appliedUserId,
    Long hostId,
    List<Long> groupLeaderIds
) implements NotificaitonEvent {}
