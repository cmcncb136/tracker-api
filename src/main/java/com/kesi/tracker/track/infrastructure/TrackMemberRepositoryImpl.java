package com.kesi.tracker.track.infrastructure;

import com.kesi.tracker.track.application.repository.TrackMemberRepository;
import com.kesi.tracker.track.domain.TrackMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackMemberRepositoryImpl implements TrackMemberRepository {
    private final TrackMemberJpaRepository trackMemberJpaRepository;

    @Override
    public TrackMember save(TrackMember trackMember) {
        return trackMemberJpaRepository.save(TrackMemberEntity.fromDomain(trackMember)).toDomain();
    }

    @Override
    public Optional<TrackMember> findById(Long id) {
        return trackMemberJpaRepository.findById(id).map(TrackMemberEntity::toDomain);
    }

    @Override
    public List<TrackMember> findByTrackAssignmentId(Long trackAssignmentId) {
        return trackMemberJpaRepository.findByTrackAssignmentId(trackAssignmentId)
                .stream().map(TrackMemberEntity::toDomain).toList();
    }

    @Override
    public List<TrackMember> findByUid(Long uid) {
        return trackMemberJpaRepository.findByUid(uid)
                .stream().map(TrackMemberEntity::toDomain).toList();
    }

    @Override
    public Optional<TrackMember> findByTrackAssignmentIdAndUid(Long trackAssignmentId, Long uid) {
        return trackMemberJpaRepository.findByTrackAssignmentIdAndUid(trackAssignmentId, uid)
                .map(TrackMemberEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        trackMemberJpaRepository.deleteById(id);
    }
}
