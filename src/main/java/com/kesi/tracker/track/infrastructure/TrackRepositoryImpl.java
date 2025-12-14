package com.kesi.tracker.track.infrastructure;

import com.kesi.tracker.track.application.repository.TrackRepository;
import com.kesi.tracker.track.domain.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryImpl implements TrackRepository {
    private final TrackJpaRepository trackJpaRepository;

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
}
