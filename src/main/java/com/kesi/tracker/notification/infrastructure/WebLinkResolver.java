package com.kesi.tracker.notification.infrastructure;

import com.kesi.tracker.notification.application.NotificationLinkResolver;
import com.kesi.tracker.notification.domain.NotificationLink;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WebLinkResolver implements NotificationLinkResolver {
    @Value("${front.url.base}")
    public String baseUrl;

    @Override
    public String createLink(NotificationLink link, Object... args) {
        return link.resolve(baseUrl, args);
    }
}
