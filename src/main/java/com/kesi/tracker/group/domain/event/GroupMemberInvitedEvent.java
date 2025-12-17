package com.kesi.tracker.group.domain.event;

import lombok.Builder;

@Builder
public record GroupMemberInvitedEvent(
        Long inviterId,
        Long invitedUserId,
        Long gid,
        String groupName) {
}
