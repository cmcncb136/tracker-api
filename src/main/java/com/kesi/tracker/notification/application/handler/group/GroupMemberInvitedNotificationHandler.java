package com.kesi.tracker.notification.application.handler.group;

import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.group.domain.event.GroupMemberInvitedEvent;
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
public class GroupMemberInvitedNotificationHandler implements NotificationEventHandler<GroupMemberInvitedEvent> {
    private final NotificationService notificationService;
    private final GroupService groupService;

    @Override
    public void handle(GroupMemberInvitedEvent event) {
        Group group = groupService.getByGid(event.gid());

        String title = "그룹 초대";
        String message = String.format("%s 그룹에 초대 요청이 들어왔습니다", group.getName());

        NotificationContent notificationContent = NotificationContent.builder()
                .type(NotificationType.ACTION)
                .category(NotificationCategory.GROUP)
                .title(title)
                .message(message)
                .confirmUrl("") //Todo. 추수 controller 생성 후 작성
                .cancelUrl("")
                .build();

        Notification notification = Notification.builder()
                .receiverId(event.invitedUserId())
                .content(notificationContent)
                .build();

        notificationService.send(notification);

    }

    @Override
    public Class<GroupMemberInvitedEvent> supports() {
        return GroupMemberInvitedEvent.class;
    }
}
