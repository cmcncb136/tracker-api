package com.kesi.tracker.notification.infrastructure;

import com.kesi.tracker.notification.domain.Notification;
import com.kesi.tracker.notification.domain.NotificationCategory;
import com.kesi.tracker.notification.domain.NotificationContent;
import com.kesi.tracker.notification.domain.NotificationType;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notifications")
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "receiver_id", nullable = false)
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationCategory category;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String message;

    @Column(name = "confirm_url")
    private String confirmUrl;

    @Column(name = "cancel_url")
    private String cancelUrl;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private Map<String, Object> metadata;


    public static NotificationEntity fromDomain(Notification domain) {

        return NotificationEntity.builder()
                .id(domain.getId())
                .receiverId(domain.getReceiverId())
                .type(domain.getContent().type())
                .category(domain.getContent().category())
                .title(domain.getContent().title())
                .message(domain.getContent().message())
                .confirmUrl(domain.getContent().confirmUrl())
                .cancelUrl(domain.getContent().cancelUrl())
                .isRead(domain.getIsRead() != null ? domain.getIsRead() : false) // null 방지
                .createdAt(domain.getCreatedAt())
                .readAt(domain.getReadAt())
                .metadata(domain.getContent().metadata())
                .build();
    }


    public Notification toDomain() {
        return Notification.builder()
                .id(this.id)
                .receiverId(this.receiverId)
                .content(NotificationContent.builder()
                        .type(this.type)
                        .category(this.category)
                        .title(this.title)
                        .message(this.message)
                        .confirmUrl(this.confirmUrl)
                        .cancelUrl(this.cancelUrl)
                        .metadata(this.metadata)
                        .build())
                .isRead(this.isRead)
                .createdAt(this.createdAt)
                .readAt(this.readAt)
                .build();
    }
}