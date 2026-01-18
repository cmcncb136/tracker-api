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
}
