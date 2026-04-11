package com.kesi.tracker.file.application.mapper;

import com.kesi.tracker.file.application.dto.FileResponse;
import com.kesi.tracker.file.application.dto.FileUploadResponse;
import com.kesi.tracker.file.application.storage.FileUrlAccessPolicy;
import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.file.domain.FileMetadata;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class FileMapper {
    private final FileUrlAccessPolicy fileUrlAccessPolicy;

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

    public FileResponse toFileResponse(File file) {
        return FileResponse.builder()
                .originalFileName(file.getMetadata().originalFileName())
                .size(file.getMetadata().fileSize())
                .url(fileUrlAccessPolicy.generate(file.getStorageKey()))
                .build();
    }
}
