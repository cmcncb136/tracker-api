package com.kesi.tracker.notification.presentation;

import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.notification.application.NotificationService;
import com.kesi.tracker.notification.application.dto.NotificationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Notification API", description = "시스템 알림 관리")
@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @Operation(summary = "내 알림 목록 조회")
    @GetMapping("notifications")
    public Page<NotificationResponse> findMyNotifications(
            @UserId Long userId,
            @PageableDefault Pageable pageable
    ) {
        return notificationService.findNotificationByReceiverId(userId, pageable);
    }

    @Operation(summary = "알림 읽음 처리")
    @PatchMapping("notifications/{notificationId}/read")
    public void markRead(
            @PathVariable Long notificationId,
            @UserId Long userId) {
        notificationService.read(notificationId, userId);
    }
}
