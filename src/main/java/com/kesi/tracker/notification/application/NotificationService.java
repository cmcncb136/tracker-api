package com.kesi.tracker.notification.application;

import com.kesi.tracker.notification.domain.Notification;

import java.util.Map;

public interface NotificationService {
    void send(Notification notification);
    void read(Long notificationId);
}
