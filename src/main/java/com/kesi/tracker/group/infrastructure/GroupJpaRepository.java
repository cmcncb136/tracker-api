package com.kesi.tracker.group.infrastructure;

import com.kesi.tracker.group.domain.AccessType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupJpaRepository extends JpaRepository<GroupEntity, Long> {
    List<GroupEntity> searchByName(String name);

    @Query("""
    SELECT g
    FROM GroupEntity g
    JOIN GroupMemberEntity gm on g.gid = gm.gid
    WHERE gm.uid = :uid
    """)
    List<GroupEntity> findByUid(Long uid);

    List<GroupEntity> findByGidIn(List<Long> gids);

    Page<GroupEntity> findByNameContainingIgnoreCaseAndAccess(String name, AccessType access, Pageable pageable);


    @Modifying
    @Query("""
    update GroupEntity g
    set g.memberCount = g.memberCount + 1
    where g.gid = :gid
    """)
    void increaseMemberCount(Long gid);

    @Modifying
    @Query("""
    update GroupEntity g
    set g.memberCount = g.memberCount - 1
    where g.gid = :gid
      and g.memberCount > 0
    """)
    int decreaseMemberCount(Long gid);

}
