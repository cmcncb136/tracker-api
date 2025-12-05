package com.kesi.tracker.user.application.repository;

import com.kesi.tracker.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findByEmail(String email);
    List<User> findByGid(Long gid);
    List<User> findByTrackId(Long trackId);
}