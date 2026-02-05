package com.kesi.tracker.notification.application.dto;

import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationContent;
import com.kesi.tracker.notification.domain.NotificationType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class NotificationResponse {
    private Long id;
    private NotificationType type;
    private NotificationCategory category;
    private String title;
    private String message;
    private String confirmUrl;
    private String cancelUrl;
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
