package com.kesi.tracker.track.application.handler;

import com.kesi.tracker.notification.application.NotificationService;
import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationContent;
import com.kesi.tracker.notification.domain.NotificationType;
import com.kesi.tracker.track.domain.event.TrackCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrackEventHandler {
    private final NotificationService notificationService;

    @EventListener
    public void handleTrackCreatedEvent(TrackCreatedEvent event) {
        String title = "트랙 생성 알림";
        String message = String.format("%s 그룹에 %s님이 %s 트랙을 생성했어요",
                event.groupName(), event.createdByUserName(), event.trackName());

        NotificationContent notificationContent = NotificationContent.builder()
                .type(NotificationType.CONFIRMATION)
                .category(NotificationCategory.TRACK)
                .title(title)
                .message(message)
                .confirmUrl("")
                .build();

        Notification notification = Notification.builder()
                .receiverId(event.groupLeaderId())
                .content(notificationContent)
                .build();

        notificationService.send(notification);
    }
}