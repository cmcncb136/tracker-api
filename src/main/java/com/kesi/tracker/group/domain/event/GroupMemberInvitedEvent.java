package com.kesi.tracker.group.domain.event;

import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.Builder;

@Builder
public record GroupMemberInvitedEvent(
        Long inviterId,
        Long invitedUserId,
        Long gid) implements NotificaitonEvent {
}
