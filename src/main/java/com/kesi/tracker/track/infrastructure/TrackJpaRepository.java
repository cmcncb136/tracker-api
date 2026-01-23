package com.kesi.tracker.track.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackJpaRepository  extends JpaRepository<TrackEntity, Long> {
    List<TrackEntity> findByGid(@Param("gid") Long gid);

    @Query("""
    SELECT t FROM TrackEntity t
    JOIN TrackAssignmentEntity ta ON t.id = ta.trackId
    JOIN TrackMemberEntity tm ON ta.id = tm.trackAssignmentId
    WHERE tm.uid = :uid
    """)
    List<TrackEntity> findByUid(@Param("uid") Long uid);

    List<TrackEntity> findByTitleContainingIgnoreCaseAndGid(String title, Long gid);
}
