package com.kesi.tracker.notification.presentation;

import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.notification.application.NotificationService;
import com.kesi.tracker.notification.application.dto.NotificationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("notifications")
    public Page<NotificationResponse> findMyNotifications(
            @UserId Long userId,
            @PageableDefault Pageable pageable
    ) {
        return notificationService.findNotificationByReceiverId(userId, pageable);
    }

    @PatchMapping("notifications/{notificationId}/read")
    public void markRead(
            @PathVariable Long notificationId,
            @UserId Long userId) {
        notificationService.read(notificationId, userId);
    }
}
