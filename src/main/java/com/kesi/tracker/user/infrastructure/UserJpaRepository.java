package com.kesi.tracker.user.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query("""
    SELECT u FROM UserEntity u
    JOIN GroupMemberEntity gm ON u.id = gm.uid
    WHERE gm.gid = :gid
    """)
    List<UserEntity> findByGid(Long gid);

    @Query("""
    SELECT u FROM UserEntity u
    JOIN TrackMemberEntity tm ON u.id = tm.uid
    WHERE tm.trackId = :trackId
    """)
    List<UserEntity> findByTrackId(Long trackId);
}
