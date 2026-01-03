package com.kesi.tracker.file.infrastructure;

import com.kesi.tracker.file.domain.FileMetadata;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMetadataEmbeddable {
    @Column(name = "content_type", length = 100)
    private String contentType;

    @Column(name = "virtual_name")
    private String virtualName;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static FileMetadataEmbeddable fromDomain(FileMetadata metadata) {
        return FileMetadataEmbeddable.builder()
                .contentType(metadata.contentType())
                .virtualName(metadata.virtualName())
                .originalFileName(metadata.originalFileName())
                .fileSize(metadata.fileSize())
                .createdAt(metadata.createdAt())
                .build();
    }

    public FileMetadata toDomain() {
        return new FileMetadata(
                contentType,
                virtualName,
                originalFileName,
                fileSize,
                createdAt
        );
    }

}
