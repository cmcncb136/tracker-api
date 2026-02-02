package com.kesi.tracker.notification.application;

import com.kesi.tracker.notification.domain.NotificationLink;

public interface NotificationLinkResolver {
    String createLink(NotificationLink link, Object... args);
}
