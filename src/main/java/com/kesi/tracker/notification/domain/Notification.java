package com.kesi.tracker.notification.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Map;
@Getter
@Builder
public class Notification {
    private Long id;
    private Long receiverId;
    private NotificationContent content;

    @Builder.Default
    private Boolean isRead = false;
    @Builder.Default
    private LocalDateTime createdAt =  LocalDateTime.now();

    private LocalDateTime readAt;


    public void read() {
        this.readAt = LocalDateTime.now();
        this.isRead = true;
    }
}