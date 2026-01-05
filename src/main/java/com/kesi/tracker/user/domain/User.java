package com.kesi.tracker.user.domain;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;

import java.time.LocalDateTime;

@Getter
public class User {
    private static final int MAX_PASSWORD_LENGTH = 128;

    private Long id;
    private Email email;
    private String password;
    private String name;
    private String nickname;
    @Nullable private Phone phone;
    private LocalDateTime birthday;
    private LocalDateTime createdAt;
    private Role role;

    @Builder
    public User(Long id, Email email, String password, String name, String nickname, Phone phone, LocalDateTime birthday, LocalDateTime createdAt, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone = phone;
        this.birthday = birthday;
        this.createdAt = createdAt;
        this.role = role;

        if(ObjectUtils.isEmpty(email))
            throw new IllegalArgumentException("email is empty");

        if(ObjectUtils.isEmpty(password))
            throw new IllegalArgumentException("Password cannot be empty");

        if(ObjectUtils.isEmpty(name))
            throw new IllegalArgumentException("Name cannot be empty");

        if(ObjectUtils.isEmpty(nickname))
            throw new IllegalArgumentException("Nickname cannot be empty");

        if(ObjectUtils.isEmpty(birthday))
            throw new IllegalArgumentException("Birthday cannot be empty");



        if(password.length() > MAX_PASSWORD_LENGTH)
            throw new IllegalArgumentException("Password length exceeds maximum length of 32");

    }
}
