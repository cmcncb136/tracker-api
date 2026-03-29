package com.kesi.tracker.notification.application.dto;

import com.kesi.tracker.notification.application.model.query.NotificationSearch;
import com.kesi.tracker.notification.domain.NotificationType;
import jakarta.annotation.Nullable;
import lombok.Data;

@Data
public class NotificationSearchRequest {
    @Nullable
    private NotificationType type;
    @Nullable
    private String message;
    @Nullable
    private Boolean isRead;

    public NotificationSearch toNotificationSearch(Long receiverId) {
        return NotificationSearch.builder()
                .type(this.type)
                .message(this.message)
                .isRead(this.isRead)
                .receiverId(receiverId)
                .build();
    }
}
