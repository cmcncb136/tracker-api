package com.kesi.tracker.file.application.mapper;

import com.kesi.tracker.file.application.dto.FileUploadResponse;
import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileMetadata;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class FileMapper {
    public static FileMetadata toFileMetadata(MultipartFile file, String virtualName) {
        return FileMetadata.builder()
                .virtualName(virtualName)
                .contentType(file.getContentType())
                .originalFileName(file.getOriginalFilename())
                .fileSize(file.getSize())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static FileUploadResponse toFileUploadResponse(File file) {
        return FileUploadResponse.builder()
                .id(file.getId())
                .storageKey(file.getStorageKey().value())
                .build();
    }
}
