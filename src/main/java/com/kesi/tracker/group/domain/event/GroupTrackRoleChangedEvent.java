package com.kesi.tracker.group.domain.event;

import com.kesi.tracker.group.domain.GroupTrackRole;
import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.Builder;

@Builder
public record GroupTrackRoleChangedEvent(
   Long groupId,
   Long roleChangedUserId,
   GroupTrackRole changedRole
) implements NotificaitonEvent {}
