package com.kesi.tracker.user.infrastructure;

import com.kesi.tracker.user.application.repository.UserRepository;
import com.kesi.tracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return userJpaRepository.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(UserEntity::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(UserEntity::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return userJpaRepository.existsByPhone(phone);
    }

    @Override
    public List<User> findByGid(Long gid) {
        return userJpaRepository.findByGid(gid)
                .stream().map(UserEntity::toDomain).toList();
    }

    @Override
    public List<User> findByTrackId(Long trackId) {
        return userJpaRepository.findByTrackId(trackId)
                .stream().map(UserEntity::toDomain).toList();
    }
}
