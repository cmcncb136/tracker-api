package com.kesi.tracker.track.application.dto;

public enum TrackRoleFilter {
    HOST,
    FOLLOWER,
    ALL;

    public boolean isAll() {
        return this.equals(TrackRoleFilter.ALL);
    }
}
