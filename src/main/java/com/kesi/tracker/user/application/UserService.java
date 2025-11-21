package com.kesi.tracker.user.application;

import com.kesi.tracker.user.domain.User;


import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User getById(Long id);
    User save(User user);
}
