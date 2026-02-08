package com.kesi.tracker.core.response;

import com.kesi.tracker.core.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "에러 응답 공통 구조")
@Builder
public record ErrorResponse (
    @Schema(description = "에러 메시지", example = "해당 리소스를 찾을 수 없습니다.")
    String message,
    @Schema(description = "에러 코드", example = "ENTITY_NOT_FOUND")
    String errorCode
) {
    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .message(errorCode.getMessage())
                .errorCode(errorCode.name())
                .build();
    }
}
