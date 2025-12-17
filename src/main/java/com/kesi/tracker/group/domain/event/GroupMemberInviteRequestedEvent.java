package com.kesi.tracker.group.domain.event;

import lombok.Builder;

@Builder
public record GroupMemberInviteRequestedEvent (
    Long leaderId,
    Long groupId,
    String groupName,
    String requestedUserEmail,
    String requestedUserNickname
) {}
