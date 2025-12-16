package com.kesi.tracker.notification.application;

import com.kesi.tracker.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface NotificationService {
    void send(Notification notification);
    void read(Long notificationId);

    List<Notification> findByReceiverId(Long receiverId);
    Page<Notification> findByReceiverId(Long receiverId, Pageable pageable);
}
