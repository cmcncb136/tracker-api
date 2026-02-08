package com.kesi.tracker.notice.infrastructure;


import com.kesi.tracker.notice.domain.Notice;
import com.kesi.tracker.notice.domain.NoticeType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "notices")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gid")
    private Long gid;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NoticeType type;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Column(name = "created_by", updatable = false)
    private Long createdBy;

    @Column(name = "modified_by")
    private Long modifiedBy;

    /**
     * Domain -> Entity 변환
     */
    public static NoticeEntity fromDomain(Notice domain) {
        return NoticeEntity.builder()
                .id(domain.getId())
                .gid(domain.getGid())
                .authorId(domain.getAuthorId())
                .type(domain.getType())
                .title(domain.getTitle())
                .content(domain.getContent())
                .createdAt(domain.getCreatedAt() != null ? domain.getCreatedAt() : LocalDateTime.now())
                .modifiedAt(domain.getModifiedAt())
                .createdBy(domain.getCreatedBy())
                .modifiedBy(domain.getModifiedBy())
                .build();
    }

    /**
     * Entity -> Domain 변환
     */
    public Notice toDomain() {
        return Notice.builder()
                .id(this.id)
                .gid(this.gid)
                .authorId(this.authorId)
                .type(this.type)
                .title(this.title)
                .content(this.content)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .createdBy(this.createdBy)
                .modifiedBy(this.modifiedBy)
                .build();
    }
}
