package com.kesi.tracker.group.infrastructure;

import com.kesi.tracker.group.domain.AccessType;
import com.kesi.tracker.group.domain.Group;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "groups") // group은 예약어라 테이블명 분리
@Builder
@AllArgsConstructor
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gid")
    private Long gid;

    @Column(nullable = false, length = 100)
    private String name;

    @Column
    private String introduce;

    @Lob
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private AccessType access;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    public Group toDomain() {
        return Group.builder()
                .gid(this.gid)
                .name(this.name)
                .introduce(this.introduce)
                .description(this.description)
                .access(this.access)
                .createdBy(this.createdBy)
                .createdAt(this.createdAt)
                .build();
    }

    public static GroupEntity fromDomain(Group group) {
        return GroupEntity.builder()
                .gid(group.getGid())
                .name(group.getName())
                .introduce(group.getIntroduce())
                .description(group.getDescription())
                .access(group.getAccess())
                .createdBy(group.getCreatedBy())
                .createdAt(group.getCreatedAt())
                .build();
    }
}