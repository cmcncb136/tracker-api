package com.kesi.tracker.file.application.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileUploadResponse {
    private Long id;
    private String storageKey;
}
