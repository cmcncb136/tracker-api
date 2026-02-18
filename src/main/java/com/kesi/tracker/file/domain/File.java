package com.kesi.tracker.file.domain;


import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class File {
    private Long id;

    @Nullable
    private FileOwner owner;
    @Nullable
    private FilePurpose purpose;

    private StorageKey storageKey;
    private FileMetadata metadata;

    public void assignAsProfile(FileOwner owner) {
        Objects.requireNonNull(owner, "Owner must not be null");

        if(!FilePurpose.PROFILE.validate(this.getMetadata()))
            throw new BusinessException(ErrorCode.INVALID_FILE_PURPOSE);

        this.purpose = FilePurpose.PROFILE;
        this.owner = owner;
    }

    public void replace(StorageKey storageKey) {
        this.storageKey = storageKey;
    }
}
