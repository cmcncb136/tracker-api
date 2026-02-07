package com.kesi.tracker.file.domain;

import lombok.Builder;

@Builder
public record FileOwner(
        Long ownerId,
        OwnerType ownerType
) {

    public static FileOwner ofUser(Long userId) {
        return FileOwner.builder()
                .ownerType(OwnerType.USER)
                .ownerId(userId)
                .build();
    }

    public static FileOwner ofGroup(Long groupId) {
        return FileOwner.builder()
                .ownerType(OwnerType.GROUP)
                .ownerId(groupId)
                .build();
    }

    public static FileOwner ofTrack(Long trackId) {
        return FileOwner.builder()
                .ownerType(OwnerType.TRACK)
                .ownerId(trackId)
                .build();
    }

    public static FileOwner ofNotice(Long noticeId) {
        return FileOwner.builder()
                .ownerType(OwnerType.NOTICE)
                .ownerId(noticeId)
                .build();
    }
}
