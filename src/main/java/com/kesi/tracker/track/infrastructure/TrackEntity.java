package com.kesi.tracker.track.infrastructure;

import com.kesi.tracker.track.domain.Track;
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
@Entity
@Table(name = "track")
public class TrackEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Long gid; // 그룹 ID

    @Column(name = "host_id", nullable = false)
    private Long hostId;

    @Column(name = "follower_cnt", nullable = false)
    private int followerCnt; // 현재 참여 인원

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String introduce; // 간단 소개

    @Lob
    private String description; // 상세 설명

    @Column
    private String place;

    @Column(nullable = false)
    private Long cost;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private Long createdBy;

    @Column(name = "modified_by", nullable = false)
    private Long modifiedBy;

    // --- Domain <-> Entity 변환 로직 ---
    public Track toDomain() {
        return Track.builder()
                .id(this.id)
                .gid(this.gid)
                .hostId(this.hostId)
                .followerCnt(this.followerCnt)
                .title(this.title)
                .introduce(this.introduce)
                .description(this.description)
                .place(this.place)
                .cost(this.cost)
                .createdAt(this.createdAt)
                .modifiedAt(this.modifiedAt)
                .createdBy(this.createdBy)
                .modifiedBy(this.modifiedBy)
                .build();
    }


    public static TrackEntity fromDomain(Track domain) {

        return TrackEntity.builder()
                .id(domain.getId())
                .gid(domain.getGid())
                .hostId(domain.getHostId())
                .followerCnt(domain.getFollowerCnt())
                .title(domain.getTitle())
                .introduce(domain.getIntroduce())
                .description(domain.getDescription())
                .place(domain.getPlace())
                .cost(domain.getCost())
                .createdAt(domain.getCreatedAt())
                .modifiedAt(domain.getModifiedAt())
                .createdBy(domain.getCreatedBy())
                .modifiedBy(domain.getModifiedBy())
                .build();
    }
}