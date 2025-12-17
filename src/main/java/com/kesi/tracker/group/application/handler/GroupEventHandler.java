package com.kesi.tracker.group.application.handler;

import com.kesi.tracker.group.application.GroupMemberService;
import com.kesi.tracker.group.application.GroupService;
import com.kesi.tracker.group.domain.event.GroupMemberInviteRequestedEvent;
import com.kesi.tracker.group.domain.event.GroupMemberInvitedEvent;
import com.kesi.tracker.notification.application.NotificationService;
import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationType;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Not;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupEventHandler {
    private final NotificationService notificationService;

    @EventListener
    public void handleGroupMemberInvitedEvent(GroupMemberInvitedEvent event) {
        String title = "그룹 초대";
        String message = String.format("%s 그룹에 초대 요청이 들어왔습니다", event.groupName());

        Notification notification = Notification.builder()
                .receiverId(event.invitedUserId())
                .type(NotificationType.ACTION)
                .category(NotificationCategory.GROUP)
                .title(title)
                .message(message)
                .confirmUrl("") //Todo. 추수 controller 생성 후 작성
                .cancelUrl("")
                .build();

        notificationService.send(notification);
    }

    @EventListener
    public void handleGroupMemberInviteRequestedEvent(GroupMemberInviteRequestedEvent event) {
        String title = "그룹 초대 요청";
        String message =
                String.format("%s(%s)님이 %s 그룹 초대를 요청했습니다",
                        event.requestedUserNickname(), event.requestedUserEmail(), event.groupName());

        Notification notification = Notification.builder()
                .receiverId(event.leaderId())
                .type(NotificationType.ACTION)
                .category(NotificationCategory.GROUP)
                .title(title)
                .message(message)
                .confirmUrl("") //Todo. 추수 controller 생성 후 작성
                .cancelUrl("")
                .build();
    }
}
