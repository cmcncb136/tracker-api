package com.kesi.tracker.notification.domain;

public enum NotificationLink {
    //group
    GROUP_INVITATION("/groups/%d/invitations"),
    GROUP_JOIN_REQUEST("/groups/%d/join-requests"),
    GROUP_INVITATION_ACCEPT("/groups/%d/invitation/accept"),
    GROUP_JOIN_REQUEST_APPROVED("/groups/%d/join-requests/%d/approved"),

    //track
    TRACK_CREATED("/groups/%d/tracks/%d"),
    ;

    private final String frontendPath;

    NotificationLink(String pathTemplate) {
        this.frontendPath = pathTemplate;
    }

    public String resolve(String baseUrl, Object... args) {
        return String.format(frontendPath, args);
    }
}
