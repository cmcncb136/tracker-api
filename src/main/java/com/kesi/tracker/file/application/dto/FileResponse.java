package com.kesi.tracker.file.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "첨부 파일 응답")
@Data
@Builder
public class FileResponse {
    private String originalFileName;
    private long size;
    private String url;
}
