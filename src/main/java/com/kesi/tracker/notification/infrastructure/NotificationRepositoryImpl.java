package com.kesi.tracker.notification.infrastructure;

import com.kesi.tracker.notification.application.model.query.NotificationSearch;
import com.kesi.tracker.notification.application.repository.NotificationRepository;
import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationType;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {
    private final NotificationJpaRepository notificationJpaRepository;
    private final JPAQueryFactory queryFactory;


    @Override
    public Notification save(Notification notification) {
        return notificationJpaRepository.save(NotificationEntity.fromDomain(notification))
                .toDomain();
    }

    @Override
    public List<Notification> save(List<Notification> notifications) {
        return notificationJpaRepository
                .saveAll(notifications.stream().map(NotificationEntity::fromDomain).toList())
                .stream().map(NotificationEntity::toDomain).toList();
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

    @Override
    public Page<Notification> searchOrderByCreatedAtDesc(NotificationSearch search, Pageable pageable) {
        QNotificationEntity notificationEntity = QNotificationEntity.notificationEntity;
        List<NotificationEntity> notificationEntities
                = queryFactory.selectFrom(notificationEntity)
                .where(
                        typeEq(search.getType()),
                        messageContains(search.getMessage()),
                        isReadEq(search.getIsRead()),
                        notificationEntity.receiverId.eq(search.getReceiverId())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(notificationEntity.createdAt.desc())
                .fetch();

        Long total = queryFactory.
                select(notificationEntity.count())
                .from(notificationEntity)
                .where(
                        typeEq(search.getType()),
                        messageContains(search.getMessage()),
                        isReadEq(search.getIsRead()),
                        notificationEntity.receiverId.eq(search.getReceiverId())
                )
                .fetchOne();

        List<Notification> notifications = notificationEntities.stream().map(NotificationEntity::toDomain).toList();
        return new PageImpl<>(notifications, pageable, total != null ? total : 0L);
    }

    private BooleanExpression typeEq(NotificationType type) {
        return type != null ? QNotificationEntity.notificationEntity.type.eq(type) : null;
    }

    private BooleanExpression messageContains(String message) {
        return message != null ? QNotificationEntity.notificationEntity.message.containsIgnoreCase(message) : null;
    }

    private BooleanExpression isReadEq(Boolean isRead) {
        return isRead != null ? QNotificationEntity.notificationEntity.isRead.eq(isRead) : null;
    }
}
