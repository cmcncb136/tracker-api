package com.kesi.tracker.notification.application.dto;

import com.kesi.tracker.notification.application.model.query.NotificationSearch;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationType;
import jakarta.annotation.Nullable;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
@Schema(description = "알림 검색 요청 파라미터")
public class NotificationSearchRequest {

    @Schema(description = "알림 타입 (예: INFO, CONFIRMATION 등)", example = "INFO")
    @Nullable
    private NotificationType type;

    @Schema(description = "알림 카테고리 (예: GROUP, TRACK 등)", example = "GROUP")
    @Nullable
    private NotificationCategory category;


    @Schema(description = "검색할 알림 메시지 내용 (부분 일치)", example = "초대합니다")
    @Nullable
    private String message;

    @Schema(description = "읽음 여부 필터링 (true: 읽음, false: 안 읽음)", example = "false")
    @Nullable
    private Boolean read;

    public NotificationSearch toNotificationSearch(Long receiverId) {
        return NotificationSearch.builder()
                .type(this.type)
                .category(this.category)
                .message(this.message)
                .read(this.read)
                .receiverId(receiverId)
                .build();
    }
}
