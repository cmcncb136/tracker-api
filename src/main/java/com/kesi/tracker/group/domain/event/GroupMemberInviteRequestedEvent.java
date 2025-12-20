package com.kesi.tracker.group.domain.event;

import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.Builder;

@Builder
public record GroupMemberInviteRequestedEvent (
    Long leaderId,
    Long groupId,
    Long requestedUserId
) implements NotificaitonEvent {}
