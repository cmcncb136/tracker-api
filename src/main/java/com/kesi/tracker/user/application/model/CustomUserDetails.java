package com.kesi.tracker.user.application.model;

import com.kesi.tracker.user.domain.Role;
import com.kesi.tracker.user.domain.User;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class CustomUserDetails implements UserDetails {
    @Nullable
    private final String password;
    @Nullable
    private final String username;
    private final Role role;
    private final Long userId;

    public static CustomUserDetails from(User user) {
        return CustomUserDetails.builder()
                .username(user.getName())
                .password(user.getPassword())
                .userId(user.getId())
                .role(user.getRole())
                .build();
    }

    public static CustomUserDetails of(Long userId, Role role) {
        return CustomUserDetails.builder()
                .userId(userId)
                .role(role)
                .build();

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    public Long getId() {
        return userId;
    }
}
