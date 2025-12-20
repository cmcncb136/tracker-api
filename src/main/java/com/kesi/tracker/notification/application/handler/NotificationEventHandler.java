package com.kesi.tracker.notification.application.handler;

import com.kesi.tracker.notification.domain.NotificaitonEvent;

public interface NotificationEventHandler<T extends NotificaitonEvent> {
    void handle(T event);
    Class<T> supports(); //제너릭 타입소거 대처
}
