package com.kesi.tracker.group.infrastructure;

import com.kesi.tracker.group.domain.GroupMember;
import com.kesi.tracker.group.domain.GroupMemberStatus;
import com.kesi.tracker.group.domain.GroupRole;
import com.kesi.tracker.group.domain.GroupTrackRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "group_member")
@Builder
@AllArgsConstructor
public class GroupMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long uid;

    @Column(nullable = false)
    private Long gid;

    @Enumerated(EnumType.STRING)
    private GroupMemberStatus status;

    @Enumerated(EnumType.STRING)
    private GroupRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "group_track_role")
    private GroupTrackRole trackRole;

    @Column(name = "create_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name="modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    public GroupMember toDomain() {
        return GroupMember.builder()
                .id(id)
                .uid(uid)
                .gid(gid)
                .status(status)
                .role(role)
                .trackRole(trackRole)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .build();
    }

    public static GroupMemberEntity fromDomain(GroupMember domain) {
        return GroupMemberEntity.builder()
                .id(domain.getId()) // ID가 있으면 설정 (업데이트의 경우)
                .uid(domain.getUid())
                .gid(domain.getGid())
                .status(domain.getStatus())
                .role(domain.getRole())
                .trackRole(domain.getTrackRole())
                .createdAt(domain.getCreatedAt())
                .modifiedAt(domain.getModifiedAt())
                .build();
    }
}


