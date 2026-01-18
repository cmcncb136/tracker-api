package com.kesi.tracker.user.application;

import com.kesi.tracker.user.application.dto.MyProfileResponse;
import com.kesi.tracker.user.application.dto.UserJoinRequest;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import com.kesi.tracker.user.domain.Email;
import com.kesi.tracker.user.domain.User;


import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(Email email);
    User getByEmail(Email email);
    User getById(Long id);
    User save(User user);
    void join(UserJoinRequest dto);
    MyProfileResponse getMyProfile(Long id);
    UserProfileResponse getProfile(String email);
    UserProfileResponse getProfile(Long id);
}
