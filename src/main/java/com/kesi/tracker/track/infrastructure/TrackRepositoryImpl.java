package com.kesi.tracker.track.infrastructure;

import com.kesi.tracker.group.domain.GroupMemberStatus;
import com.kesi.tracker.group.infrastructure.QGroupMemberEntity;
import com.kesi.tracker.track.application.query.TrackSearchCondition;
import com.kesi.tracker.track.application.query.TrackWithGroupSearchCondition;
import com.kesi.tracker.track.application.repository.TrackRepository;
import com.kesi.tracker.track.domain.Track;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryImpl implements TrackRepository {
    private final TrackJpaRepository trackJpaRepository;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Track> findAll() {
        return trackJpaRepository.findAll().stream().map(TrackEntity::toDomain).toList();
    }

    @Override
    public List<Track> findByGid(Long gid) {
        return trackJpaRepository.findByGid(gid).stream().map(TrackEntity::toDomain).toList();
    }

    @Override
    public List<Track> findByUserId(Long userId) {
        return trackJpaRepository.findByUid(userId).stream().map(TrackEntity::toDomain).toList();
    }

    @Override
    public Optional<Track> findById(Long id) {
        return trackJpaRepository.findById(id).map(TrackEntity::toDomain);
    }

    @Override
    public List<Track> searchByTitleAndGid(String title, Long gid) {
        return trackJpaRepository.findByTitleContainingIgnoreCaseAndGid(title, gid).stream().map(TrackEntity::toDomain).toList();
    }

    @Override
    public Track save(Track track) {
        return trackJpaRepository.save(TrackEntity.fromDomain(track)).toDomain();
    }

    @Override
    public Page<Track> searchInGroup(Long gid, TrackSearchCondition condition, Pageable pageable) {
        QTrackEntity trackEntity = QTrackEntity.trackEntity;
        List<TrackEntity> contents = queryFactory
                .selectFrom(trackEntity)
                .where(
                        titleContains(condition.getTitle()),
                        introduceContains(condition.getIntroduction()),
                        assignmentOverlaps(condition.getAssignmentStartAt(), condition.getAssignmentEndAt()),
                        gidEq(gid)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(trackEntity.count())
                .from(trackEntity)
                .where(
                        titleContains(condition.getTitle()),
                        introduceContains(condition.getIntroduction()),
                        assignmentOverlaps(condition.getAssignmentStartAt(), condition.getAssignmentEndAt()),
                        gidEq(gid)
                )
                .fetchOne();

        List<Track> domainTracks = contents.stream().map(TrackEntity::toDomain).toList();
        return new PageImpl<>(domainTracks, pageable, total != null ? total : 0L);
    }

    @Override
    public Page<Track> searchInGroupInUser(Long uid, TrackWithGroupSearchCondition condition, Pageable pageable) {
        QTrackEntity trackEntity = QTrackEntity.trackEntity;
        QGroupMemberEntity groupMemberEntity = QGroupMemberEntity.groupMemberEntity;

        List<TrackEntity> contents = queryFactory
                .selectFrom(trackEntity)
                .join(groupMemberEntity).on(trackEntity.gid.eq(groupMemberEntity.gid))
                .where(
                        titleContains(condition.getTitle()),
                        introduceContains(condition.getIntroduction()),
                        assignmentOverlaps(condition.getAssignmentStartAt(), condition.getAssignmentEndAt()),
                        groupMemberEntity.uid.eq(uid),
                        groupMemberEntity.status.eq(GroupMemberStatus.APPROVED)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long total = queryFactory
                .select(trackEntity.count())
                .from(trackEntity)
                .join(groupMemberEntity).on(trackEntity.gid.eq(groupMemberEntity.gid))
                .where(
                        titleContains(condition.getTitle()),
                        introduceContains(condition.getIntroduction()),
                        assignmentOverlaps(condition.getAssignmentStartAt(), condition.getAssignmentEndAt()),
                        groupMemberEntity.uid.eq(uid),
                        groupMemberEntity.status.eq(GroupMemberStatus.APPROVED)
                )
                .fetchOne();

        List<Track> domainTracks = contents.stream().map(TrackEntity::toDomain).toList();
        return new PageImpl<>(domainTracks, pageable, total != null ? total : 0L);
    }

    // 동적 쿼리를 위한 BooleanExpression 메서드들
    private BooleanExpression assignmentOverlaps(
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        if (startAt == null || endAt == null) {
            return null;
        }

        return QTrackEntity.trackEntity.assignmentStartAt.loe(endAt)
                .and(QTrackEntity.trackEntity.assignmentEndAt.goe(startAt));
    }


    private BooleanExpression introduceContains(String introduce) {
        return introduce != null ? QTrackEntity.trackEntity.introduce.containsIgnoreCase(introduce) : null;
    }

    private BooleanExpression titleContains(String title) {
        return title != null ? QTrackEntity.trackEntity.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression gidEq(Long gid) {
        return gid != null ? QTrackEntity.trackEntity.gid.eq(gid) : null;
    }
}
