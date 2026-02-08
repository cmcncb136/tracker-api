package com.kesi.tracker.file.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "파일 업로드 완료 응답")
@Data
@Builder
public class FileUploadResponse {
    @Schema(description = "파일 ID")
    private Long id;
    @Schema(description = "스토리지 저장 키")
    private String storageKey;
}
