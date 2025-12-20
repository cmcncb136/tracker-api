package com.kesi.tracker.notification.application;

import com.kesi.tracker.notification.application.handler.NotificationEventHandler;
import com.kesi.tracker.notification.domain.NotificaitonEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

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


    @SuppressWarnings("unchecked")
    @Async //비동기 처리 (추후 한 transaction 으로 묶을 필요가 있으면 수정 필요)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT) //해당 event를 발행하는 메서드가 끝난 후에 해당 메서드를 호출
    public void on(NotificaitonEvent event) {
        NotificationEventHandler handler = handlerMap.get(event.getClass());
        if(handler == null) {
            log.warn("해당 이벤트를 처리할 핸드러가 없습니다. class : {}", event.getClass());
            return;
        }

        handler.handle(event);
    }
}
