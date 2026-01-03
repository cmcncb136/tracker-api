package com.kesi.tracker.file.application.model;

import com.kesi.tracker.file.domain.FileMetadata;
import lombok.Builder;

@Builder
public record UploadedFile (
        String storageKey,
        FileMetadata metadata
){ }
