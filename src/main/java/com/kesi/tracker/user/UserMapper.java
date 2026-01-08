package com.kesi.tracker.user;

import com.kesi.tracker.user.application.dto.UserJoinRequest;
import com.kesi.tracker.user.domain.Email;
import com.kesi.tracker.user.domain.Phone;
import com.kesi.tracker.user.domain.Role;
import com.kesi.tracker.user.domain.User;

import java.time.LocalDateTime;

public class UserMapper {
    public static User toDomain(UserJoinRequest dto) {
        return User.builder()
                .email(new Email(dto.getEmail()))
                .password(dto.getPassword())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .phone(new Phone(dto.getPhone()))
                .birthday(dto.getBirthday())
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
    }
}
