package com.kesi.tracker.user.domain;

import com.kesi.tracker.core.exception.BusinessException;
import com.kesi.tracker.core.exception.ErrorCode;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.ObjectUtils;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class User {
    private static final int MAX_PASSWORD_LENGTH = 128;

    private Long id;
    private Email email;
    private String password;
    private String name;
    private String nickname;
    @Nullable private Phone phone;
    private LocalDate birthday;
    private LocalDateTime createdAt;
    private Role role;

    @Builder
    public User(Long id, Email email, String password, String name, String nickname, Phone phone, LocalDate birthday, LocalDateTime createdAt, Role role) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.password = ObjectUtils.requireNonEmpty(password);
        this.name = ObjectUtils.requireNonEmpty(name);
        this.nickname = ObjectUtils.requireNonEmpty(nickname);
        this.phone = phone;
        this.birthday = birthday;
        this.createdAt = Objects.requireNonNull(createdAt);
        this.role = Objects.requireNonNull(role);

        if(password.length() > MAX_PASSWORD_LENGTH)
            throw new BusinessException(ErrorCode.INVALID_PASSWORD_LENGTH);

        //Todo. 기타 유효성 검사 (각 변수를 VO를 만들어야 할지 고민)
    }
}
