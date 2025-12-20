package com.kesi.tracker.track.domain.event;


import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.Builder;

import java.util.List;

@Builder
public record TrackCreatedEvent(
        Long groupId,
        Long trackId,
        Long createdByUserId,
        List<Long> groupLeaderIds
) implements NotificaitonEvent {
}

