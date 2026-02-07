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
import com.kesi.tracker.track.domain.event.TrackCanceledEvent;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TrackCanceledNotificationHandler implements NotificationEventHandler<TrackCanceledEvent> {
    private final NotificationService notificationService;
    private final GroupService groupService;
    private final TrackService trackService;
    private final UserService userService;


    @Override
    public void handle(TrackCanceledEvent event) {
        User canceledUser = userService.getById(event.canceledUserId());
        Group group = groupService.getByGid(event.groupId());
        Track track = trackService.getById(event.trackId());

        String title = "수강 신청 취소";
        String message = String.format("%s 그룹에서 %s님이 %s 트랙 신청을 취소했습니다",
                group.getName(), canceledUser.getNickname(), track.getTitle());

        NotificationContent notificationContent = NotificationContent.builder()
                .type(NotificationType.CONFIRMATION)
                .category(NotificationCategory.TRACK)
                .title(title)
                .message(message)
                .build();

        List<Notification> notifications = new ArrayList<>(event.groupLeaderIds().size() + 1);

        for(Long leaderId : event.groupLeaderIds()) {
            Notification notification = Notification.builder()
                    .receiverId(leaderId)
                    .content(notificationContent)
                    .build();

            notifications.add(notification);
        }

        Notification notificationFromHost = Notification.builder()
                .receiverId(event.hostId())
                .content(notificationContent)
                .build();
         notifications.add(notificationFromHost);

        notificationService.send(notifications);
    }

    @Override
    public Class<TrackCanceledEvent> supports() {
        return TrackCanceledEvent.class;
    }
}
