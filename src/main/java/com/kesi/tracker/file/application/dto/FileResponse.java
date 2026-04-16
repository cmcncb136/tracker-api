package com.kesi.tracker.file.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "첨부 파일 응답")
@Data
@Builder
public class FileResponse {
    @Schema(description = "파일 ID", example = "2")
    public Long id;
    @Schema(description = "원본 파일명", example = "profile_image.png")
    private String originalFileName;
    @Schema(description = "파일 크기 (Byte)", example = "102450")
    private long size;
    @Schema(description = "파일 url", example = "https://example.com/files/storage/notices/8/profile/a1f10949-e474-4118-892b-4cae3b52ebe8.png")
    private String url;
}
