package com.kesi.tracker.file.domain;

import lombok.Builder;

import java.util.List;

@Builder
public record FileOwners(
        List<Long> ownerIds,
        OwnerType ownerType
) {
    public static FileOwners ofUser(List<Long> userIds) {
        return FileOwners.builder()
                .ownerIds(userIds)
                .ownerType(OwnerType.USER)
                .build();
    }

    public static FileOwners ofGroup(List<Long> groupIds) {
        return FileOwners.builder()
                .ownerIds(groupIds)
                .ownerType(OwnerType.GROUP)
                .build();
    }

    public static FileOwners ofTrack(List<Long> trackIds) {
        return FileOwners.builder()
                .ownerIds(trackIds)
                .ownerType(OwnerType.TRACK)
                .build();
    }


}
