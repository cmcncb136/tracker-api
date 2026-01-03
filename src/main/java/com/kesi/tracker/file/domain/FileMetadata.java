package com.kesi.tracker.file.domain;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FileMetadata (
        String contentType,
        String virtualName,
        String originalFileName,
        Long fileSize,
        LocalDateTime createdAt
) { }
