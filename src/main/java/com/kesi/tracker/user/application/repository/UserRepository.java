package com.kesi.tracker.user.application.repository;

import com.kesi.tracker.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    List<User> findByGid(Long gid);
    List<User> findByTrackId(Long trackId);
}