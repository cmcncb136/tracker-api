package com.kesi.tracker.user.infrastructure;

import com.kesi.tracker.user.domain.Role;
import com.kesi.tracker.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users") // SQL 예약어 'user' 대신 'users'를 사용
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    @Column(length = 20)
    private String phone;

    private LocalDateTime birthday;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public User toDomain() {
        return User.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .nickname(this.nickname)
                .phone(this.phone)
                .birthday(this.birthday)
                .createdAt(this.createdAt)
                .role(this.role)
                .build();
    }

    public static UserEntity fromDomain(User domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .password(domain.getPassword())
                .name(domain.getName())
                .nickname(domain.getNickname())
                .phone(domain.getPhone())
                .birthday(domain.getBirthday())
                .createdAt(domain.getCreatedAt())
                .role(domain.getRole())
                .build();
    }
}
