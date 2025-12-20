package com.kesi.tracker.notification.application.handler.group;

import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.group.domain.event.GroupTrackRoleChangedEvent;
import com.kesi.tracker.notification.application.NotificationService;
import com.kesi.tracker.notification.application.handler.NotificationEventHandler;
import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationContent;
import com.kesi.tracker.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupTrackRoleChangedNotificationHandler implements NotificationEventHandler<GroupTrackRoleChangedEvent> {
    private final GroupService groupService;
    private final NotificationService notificationService;

    @Override
    public void handle(GroupTrackRoleChangedEvent event) {
        Group group = groupService.getByGid(event.groupId());

        String title = "그룹 트랙 Role 변경";
        String message = String.format("%s 그룹에서 Role이 %s로 변경되었어요",
                group.getName(), event.changedRole());

        NotificationContent notificationContent = NotificationContent.builder()
                .type(NotificationType.INFO)
                .category(NotificationCategory.GROUP)
                .title(title)
                .message(message)
                .confirmUrl("") //Todo. 추수 controller 생성 후 작성
                .cancelUrl("")
                .build();

        Notification notification = Notification.builder()
                .receiverId(event.roleChangedUserId())
                .content(notificationContent)
                .build();

        notificationService.send(notification);
    }

    @Override
    public Class<GroupTrackRoleChangedEvent> supports() {
        return GroupTrackRoleChangedEvent.class;
    }
}
