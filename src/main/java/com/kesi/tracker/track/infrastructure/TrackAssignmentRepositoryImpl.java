package com.kesi.tracker.track.infrastructure;

import com.kesi.tracker.track.application.repository.TrackAssignmentRepository;
import com.kesi.tracker.track.domain.TrackAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackAssignmentRepositoryImpl  implements TrackAssignmentRepository {
    private final TrackAssignmentJpaRepository trackAssignmentJpaRepository;

    @Override
    public TrackAssignment save(TrackAssignment trackAssignment) {
        return trackAssignmentJpaRepository
                .save(TrackAssignmentEntity.fromDomain(trackAssignment))
                .toDomain();
    }

    @Override
    public Optional<TrackAssignment> findById(Long id) {
        return trackAssignmentJpaRepository.findById(id)
                .map(TrackAssignmentEntity::toDomain);
    }

    @Override
    public List<TrackAssignment> findByTrackId(Long trackId) {
        return trackAssignmentJpaRepository.findByTrackId(trackId)
                .stream().map(TrackAssignmentEntity::toDomain).toList();
    }

    @Override
    public void deleteById(Long id) {
        trackAssignmentJpaRepository.deleteById(id);
    }
}
