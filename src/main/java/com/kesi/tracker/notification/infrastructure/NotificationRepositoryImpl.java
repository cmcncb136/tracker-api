package com.kesi.tracker.notification.infrastructure;

import com.kesi.tracker.notification.application.repository.NotificationRepository;
import com.kesi.tracker.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationJpaRepository notificationJpaRepository;


    @Override
    public Notification save(Notification notification) {
        return notificationJpaRepository.save(NotificationEntity.fromDomain(notification))
                .toDomain();
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationJpaRepository.findById(id).map(NotificationEntity::toDomain);
    }

    @Override
    public List<Notification> findByReceiverId(Long receiverId) {
        return notificationJpaRepository.findByReceiverId(receiverId)
                .stream().map(NotificationEntity::toDomain).toList();
    }

    @Override
    public Page<Notification> findByReceiverId(Long receiverId, Pageable pageable) {
        return notificationJpaRepository.findByReceiverId(receiverId, pageable)
                .map(NotificationEntity::toDomain);
    }
}
