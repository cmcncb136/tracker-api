package com.kesi.tracker.track.domain.event;

public record TrackCanceledEvent (
        String groupName,
        String trackName,
        String canceledUserName,

        Long groupId,
        Long trackId,
        Long appliedUserId,
        Long hostId,
        Long groupLeaderId
) {}
