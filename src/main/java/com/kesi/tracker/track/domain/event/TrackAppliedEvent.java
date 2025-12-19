package com.kesi.tracker.track.domain.event;

public record TrackAppliedEvent (
    String groupName,
    String trackName,
    String appliedUserName,

    Long groupId,
    Long trackId,
    Long appliedUserId,
    Long hostId,
    Long groupLeaderId
) {}
