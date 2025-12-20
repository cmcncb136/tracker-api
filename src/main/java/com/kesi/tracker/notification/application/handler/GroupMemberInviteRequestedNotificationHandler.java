package com.kesi.tracker.notification.application.handler;

import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.domain.Group;
import com.kesi.tracker.group.domain.event.GroupMemberInviteRequestedEvent;
import com.kesi.tracker.notification.application.NotificationService;
import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationContent;
import com.kesi.tracker.notification.domain.NotificationType;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupMemberInviteRequestedNotificationHandler implements NotificationEventHandler<GroupMemberInviteRequestedEvent> {
    private final NotificationService notificationService;
    private final GroupService groupService;
    private final UserService userService;

    @Override
    public void handle(GroupMemberInviteRequestedEvent event) {
        User requestedUser = userService.getById(event.requestedUserId());
        Group group = groupService.getByGid(event.groupId());

        String title = "그룹 초대 요청";
        String message =
                String.format("%s(%s)님이 %s 그룹 초대를 요청했습니다",
                        requestedUser.getNickname(), requestedUser.getEmail(), group.getName());

        NotificationContent notificationContent = NotificationContent.builder()
                .type(NotificationType.ACTION)
                .category(NotificationCategory.GROUP)
                .title(title)
                .message(message)
                .confirmUrl("") //Todo. 추수 controller 생성 후 작성
                .cancelUrl("")
                .build();

        Notification notification = Notification.builder()
                .receiverId(event.leaderId())
                .content(notificationContent)
                .build();

        notificationService.send(notification);
    }

    @Override
    public Class<GroupMemberInviteRequestedEvent> supports() {
        return GroupMemberInviteRequestedEvent.class;
    }
}
