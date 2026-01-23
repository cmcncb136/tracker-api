package com.kesi.tracker.track.infrastructure;

import com.kesi.tracker.track.domain.TrackAssignment;
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
@Table(name = "track_assignment")
@Entity
public class TrackAssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "track_id", nullable = false)
    private Long trackId;

    @Column(name = "start_at", nullable = false)
    private LocalDateTime startAt;

    @Column(name = "end_at", nullable = false)
    private LocalDateTime endAt;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Column(name = "max_capacity", nullable = false)
    private Integer maxCapacity;

    public TrackAssignment toDomain() {
        return TrackAssignment.builder()
                .id(id)
                .trackId(trackId)
                .startAt(startAt)
                .endAt(endAt)
                .capacity(capacity)
                .maxCapacity(maxCapacity)
                .build();
    }

    public static TrackAssignmentEntity fromDomain(TrackAssignment domain) {
        return TrackAssignmentEntity.builder()
                .id(domain.getId())
                .trackId(domain.getTrackId())
                .startAt(domain.getStartAt())
                .endAt(domain.getEndAt())
                .capacity(domain.getCapacity())
                .maxCapacity(domain.getMaxCapacity())
                .build();
    }
}
