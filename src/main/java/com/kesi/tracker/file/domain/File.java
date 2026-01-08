package com.kesi.tracker.file.domain;


import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public void assignFileOwner(FileOwner owner) {
        this.owner = owner;
    }

    public void assignFilePurpose(FilePurpose purpose) {

        this.purpose = purpose;
    }
}
