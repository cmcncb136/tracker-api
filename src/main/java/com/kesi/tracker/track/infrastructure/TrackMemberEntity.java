package com.kesi.tracker.track.infrastructure;

import com.kesi.tracker.track.domain.TrackMember;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "track_member_entity")
@Entity
public class TrackMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "track_id", nullable = false)
    Long trackId;

    @Column(nullable = false)
    Long uid;

    @Column(name = "created_at", nullable = false)
    LocalDateTime createdAt;

    public TrackMember toDomain() {
        return TrackMember.builder()
                .id(id)
                .uid(uid)
                .trackId(trackId)
                .createdAt(createdAt)
                .build();
    }

    public static TrackMemberEntity fromDomain(TrackMember domain) {
        return TrackMemberEntity.builder()
                .id(domain.getId())
                .trackId(domain.getTrackId())
                .uid(domain.getUid())
                .createdAt(domain.getCreatedAt())
                .build();
    }
}
