package com.kesi.tracker.notification.application.dto;

import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationContent;
import com.kesi.tracker.notification.domain.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Schema(description = "알림 내역 응답")
@Data
@Builder
public class NotificationResponse {
    @Schema(description = "알림 ID")
    private Long id;
    @Schema(description = "알림 타입 (INVITE, REQUEST, INFO)")
    private NotificationType type;
    @Schema(description = "알림 카테고리 (GROUP, TRACK, USER)")
    private NotificationCategory category;
    @Schema(description = "알림 제목")
    private String title;
    @Schema(description = "알림 메시지 본문")
    private String message;
    @Schema(description = "확인/수락 시 이동 URL")
    private String confirmUrl;
    @Schema(description = "취소/거절 시 이동 URL")
    private String cancelUrl;
    @Schema(description = "추가 메타데이터")
    private Map<String, Object> metadata;

    public static NotificationResponse from(Notification notification) {
        NotificationContent content = notification.getContent();

        return NotificationResponse.builder()
                .id(notification.getId())
                .type(content.type())
                .category(content.category())
                .title(content.title())
                .message(content.message())
                .confirmUrl(content.confirmUrl())
                .cancelUrl(content.cancelUrl())
                .metadata(content.metadata())
                .build();
    }
}
