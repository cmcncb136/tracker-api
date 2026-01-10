package com.kesi.tracker.file.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileOwner {
    Long ownerId;
    OwnerType ownerType;

    public static FileOwner ofUser(Long userId) {
        return FileOwner.builder()
                .ownerType(OwnerType.USER)
                .ownerId(userId)
                .build();
    }
}
