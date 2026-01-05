package com.kesi.tracker.user.application;

import com.kesi.tracker.user.application.dto.UserJoinRequest;
import com.kesi.tracker.user.domain.User;


import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);
    User getByEmail(String email);
    User getById(Long id);
    User save(User user);
    void join(UserJoinRequest dto);
}
