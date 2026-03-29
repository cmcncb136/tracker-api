package com.kesi.tracker.notification.application.model.query;

import com.kesi.tracker.notification.domain.NotificationCategory;
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
    private NotificationCategory category;
    @Nullable
    private String message;
    @Nullable
    private Boolean read;
    private Long receiverId;
}
