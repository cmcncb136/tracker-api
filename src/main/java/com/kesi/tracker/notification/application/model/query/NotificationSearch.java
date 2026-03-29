package com.kesi.tracker.notification.application.model.query;

import com.kesi.tracker.notification.domain.NotificationType;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationSearch {
    @Nullable
    private NotificationType type;
    @Nullable
    private String message;
    @Nullable
    private Boolean isRead;
    private Long receiverId;
}
