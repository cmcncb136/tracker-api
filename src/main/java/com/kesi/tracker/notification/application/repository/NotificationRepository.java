package com.kesi.tracker.notification.application.repository;

import com.kesi.tracker.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository {
    Notification save(Notification notification);
    Optional<Notification> findById(Long id);
    List<Notification> findByReceiverId(Long receiverId);
    Page<Notification> findByReceiverId(Long receiverId, Pageable pageable);
}
