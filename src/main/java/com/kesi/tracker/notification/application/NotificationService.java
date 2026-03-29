package com.kesi.tracker.notification.application;

import com.kesi.tracker.notification.application.dto.NotificationResponse;
import com.kesi.tracker.notification.application.dto.NotificationSearchRequest;
import com.kesi.tracker.notification.application.model.query.NotificationSearch;
import com.kesi.tracker.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface NotificationService {
    void send(Notification notification);
    void send(List<Notification> notifications);
    void read(Long notificationId, Long currentUserId);
    List<Notification> findByReceiverId(Long receiverId);
    Page<Notification> searchNotification(NotificationSearch search, Pageable pageable);
    Page<NotificationResponse> searchNotification(NotificationSearchRequest searchRequest, Long currentUid, Pageable pageable);
}
