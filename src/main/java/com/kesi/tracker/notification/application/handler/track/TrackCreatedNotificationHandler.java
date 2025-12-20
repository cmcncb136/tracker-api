package com.kesi.tracker.notification.application.handler.track;

import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.notification.application.NotificationService;
import com.kesi.tracker.notification.application.handler.NotificationEventHandler;
import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationContent;
import com.kesi.tracker.notification.domain.NotificationType;
import com.kesi.tracker.track.application.TrackService;
import com.kesi.tracker.track.domain.Track;
import com.kesi.tracker.track.domain.event.TrackCreatedEvent;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackCreatedNotificationHandler implements NotificationEventHandler<TrackCreatedEvent> {
    private final NotificationService notificationService;
    private final GroupService groupService;
    private final TrackService trackService;
    private final UserService userService;

    @Override
    public void handle(TrackCreatedEvent event) {
        User createdBy = userService.getById(event.createdByUserId());
        Group group = groupService.getByGid(event.groupId());
        Track track = trackService.getById(event.trackId());

        String title = "트랙 생성 알림";
        String message = String.format("%s 그룹에 %s님이 %s 트랙을 생성했어요",
                group.getName(), createdBy.getNickname(), track.getTitle());

        NotificationContent notificationContent = NotificationContent.builder()
                .type(NotificationType.CONFIRMATION)
                .category(NotificationCategory.TRACK)
                .title(title)
                .message(message)
                .confirmUrl("")
                .build();



        List<Notification> notifications = event.groupLeaderIds().stream().map(leaderId ->
                Notification.builder()
                        .receiverId(leaderId)
                        .content(notificationContent)
                        .build()
        ).toList();
        notificationService.send(notifications);
    }

    @Override
    public Class<TrackCreatedEvent> supports() {
        return TrackCreatedEvent.class;
    }
}
