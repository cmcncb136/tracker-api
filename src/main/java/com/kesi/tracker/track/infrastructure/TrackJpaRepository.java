package com.kesi.tracker.track.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TrackJpaRepository  extends JpaRepository<TrackEntity, Long> {
    List<TrackEntity> findByGid(@Param("gid") Long gid);

    @Query("""
    SELECT t FROM TrackEntity t
    JOIN TrackMemberEntity tm ON t.id = tm.trackId
    WHERE tm.uid = :uid
    """)
    List<TrackEntity> findByUid(@Param("uid") Long uid);

    List<TrackEntity> findByTitleContainingIgnoreCaseAndGid(String title, Long gid);

    @Modifying
    @Query("""
    UPDATE TrackEntity t
    SET t.followerCnt = t.followerCnt + 1
    WHERE t.id = :id
        AND t.followerCnt < t.capacity
    """)
    int apply(Long id);


    @Modifying
    @Query("""
    UPDATE TrackEntity t
    SET t.followerCnt = t.followerCnt - 1
    WHERE t.id = :id
    AND EXISTS (
            SELECT 1 FROM TrackMemberEntity tm
            WHERE 
                tm.trackId = :id
                 AND tm.uid = :uid
        )
    """)
    int cancel(Long id, Long uid);
}
