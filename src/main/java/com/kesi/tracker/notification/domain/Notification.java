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
    private NotificationType type;
    private NotificationCategory category;
    private String title;
    private String message;

    /** ACTION or CONFIRMATION 타입에서만 사용 */
    private String confirmUrl;
    private String cancelUrl;

    private Boolean isRead;
    private LocalDateTime createdAt;
    private LocalDateTime readAt;

    /** 타입별 추가 데이터를 넣기 위한 확장 영역 */
    private Map<String, Object> metadata;
}