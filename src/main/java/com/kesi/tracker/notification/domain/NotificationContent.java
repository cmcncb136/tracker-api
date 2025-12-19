package com.kesi.tracker.notification.domain;

import lombok.Builder;

import java.util.Map;

@Builder
public record NotificationContent (
        NotificationType type,
        NotificationCategory category,
        String title,
        String message,

        /** ACTION or CONFIRMATION 타입에서만 사용 */
        String confirmUrl,
        String cancelUrl,

        /** 타입별 추가 데이터를 넣기 위한 확장 영역 */
        Map<String, Object> metadata
) { }
