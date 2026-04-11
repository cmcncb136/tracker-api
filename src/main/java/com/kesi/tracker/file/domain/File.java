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
        this.assign(owner, FilePurpose.PROFILE);
    }

    public void assignAsAttachment(FileOwner owner) {
        this.assign(owner, FilePurpose.ATTACHMENT);
    }

    public void assign(FileOwner owner, FilePurpose purpose) {
        Objects.requireNonNull(owner, "Owner must not be null");
        Objects.requireNonNull(purpose, "Purpose must not be null");

        if(!purpose.validate(this.getMetadata()))
            throw new BusinessException(ErrorCode.INVALID_FILE_PURPOSE);

        this.purpose = purpose;
        this.owner = owner;
    }

    public void replace(StorageKey storageKey) {
        this.storageKey = storageKey;
    }
}
