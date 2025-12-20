package com.kesi.tracker.track.domain.event;

import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.Builder;

import java.util.List;

@Builder
public record TrackCanceledEvent (
        Long groupId,
        Long trackId,
        Long canceledUserId,
        Long hostId,
        List<Long> groupLeaderIds
) implements NotificaitonEvent {}
