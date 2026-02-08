package com.kesi.tracker.core.response;

import com.kesi.tracker.core.exception.ErrorCode;
import lombok.Builder;

@Builder
public record ErrorResponse (
    String message,
    String errorCode
) {
    public static ErrorResponse from(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .message(errorCode.getMessage())
                .errorCode(errorCode.name())
                .build();
    }
}
