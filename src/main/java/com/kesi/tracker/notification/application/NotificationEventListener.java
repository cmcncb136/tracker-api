package com.kesi.tracker.notification.application;

import com.kesi.tracker.notification.application.handler.NotificationEventHandler;
import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class NotificationEventListener {
    private final Map<Class<? extends NotificaitonEvent>,
            NotificationEventHandler<? extends NotificaitonEvent>> handlerMap;

    public NotificationEventListener(List<NotificationEventHandler<? extends NotificaitonEvent>> handlers) {
        handlerMap = handlers.stream().collect(Collectors.toMap(NotificationEventHandler::supports, Function.identity()));
    }

    @EventListener
    @SuppressWarnings("unchecked")
    public void on(NotificaitonEvent event) {
        NotificationEventHandler handler = handlerMap.get(event.getClass());
        if(handler == null) {
            log.warn("해당 이벤트를 처리할 핸드러가 없습니다. class : {}", event.getClass());
            return;
        }

        handler.handle(event);
    }
}
