package com.kesi.tracker.track.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class TrackMember {
    private Long id;
    private Long trackId;
    private Long uid;
    private TrackRole role;
    private LocalDateTime createdAt;


    public static TrackMember createdHost(Long trackId, Long uid) {
        return TrackMember.builder()
                .trackId(trackId)
                .uid(uid)
                .role(TrackRole.HOST)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static TrackMember createFollower(Long trackId, Long uid) {
        return TrackMember.builder()
                .trackId(trackId)
                .uid(uid)
                .role(TrackRole.FOLLOWER)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public boolean isHost() {
        return TrackRole.HOST.equals(role);
    }
}