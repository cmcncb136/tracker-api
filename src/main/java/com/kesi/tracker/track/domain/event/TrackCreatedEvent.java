package com.kesi.tracker.track.domain.event;


public record TrackCreatedEvent(
        String groupName,
        String createdByUserName,
        String trackName,

        Long groupId,
        Long trackId,
        Long createdByUserId,
        Long groupLeaderId
) {
}

