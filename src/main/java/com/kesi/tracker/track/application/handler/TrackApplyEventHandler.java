package com.kesi.tracker.track.application.handler;

import com.kesi.tracker.notification.application.NotificationService;
import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationContent;
import com.kesi.tracker.notification.domain.NotificationType;
import com.kesi.tracker.track.domain.event.TrackAppliedEvent;
import com.kesi.tracker.track.domain.event.TrackCanceledEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackApplyEventHandler {
    private final NotificationService notificationService;

    @EventListener
    public void handleTrackAppliedEvent(TrackAppliedEvent event) {
        String title = "수강 신청";
        String message = String.format("%s 그룹에서 %s님이 %s 트랙 신청을 했습니다",
                event.groupName(), event.appliedUserName(), event.trackName());

        NotificationContent notificationContent = NotificationContent.builder()
                .type(NotificationType.CONFIRMATION)
                .category(NotificationCategory.TRACK)
                .title(title)
                .message(message)
                .confirmUrl("")
                .build();


        Notification notificationFromGroupLeader = Notification.builder()
                .receiverId(event.groupLeaderId())
                .content(notificationContent)
                .build();

        Notification notificationFromHost = Notification.builder()
                .receiverId(event.hostId())
                .content(notificationContent)
                .build();


        notificationService.send(List.of(notificationFromGroupLeader, notificationFromHost));
    }

    @EventListener
    public void handleTrackCanceledEvent(TrackCanceledEvent event) {
        String title = "수강 신청 취소";
        String message = String.format("%s 그룹에서 %s님이 %s 트랙 신청을 취소했습니다",
                event.groupName(), event.canceledUserName(), event.trackName());

        NotificationContent notificationContent = NotificationContent.builder()
                .type(NotificationType.CONFIRMATION)
                .category(NotificationCategory.TRACK)
                .title(title)
                .message(message)
                .confirmUrl("x")
                .build();


        Notification notificationFromGroupLeader = Notification.builder()
                .receiverId(event.groupLeaderId())
                .content(notificationContent)
                .build();

        Notification notificationFromHost = Notification.builder()
                .receiverId(event.hostId())
                .content(notificationContent)
                .build();


        notificationService.send(List.of(notificationFromGroupLeader, notificationFromHost));
    }
}
