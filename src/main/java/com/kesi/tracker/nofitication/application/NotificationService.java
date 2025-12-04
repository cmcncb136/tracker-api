package com.kesi.tracker.nofitication.application;

import java.util.Map;

public interface NotificationService {
    void send(String title, String message);
    void send(String title, String message, Map<String, Object> data);
}
