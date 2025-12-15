package com.kesi.tracker.notification.infrastructure;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {
    List<NotificationEntity> findByReceiverId(Long receiverId);
    Page<NotificationEntity> findByReceiverId(Long receiverId, Pageable pageable);
}
