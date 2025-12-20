package com.kesi.tracker.group.domain.event;

import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.Builder;

import java.util.List;

@Builder
public record GroupMemberInviteRequestedEvent (
    List<Long> leaderIds,
    Long groupId,
    Long requestedUserId
) implements NotificaitonEvent {}
