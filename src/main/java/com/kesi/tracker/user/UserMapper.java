package com.kesi.tracker.user;

import com.kesi.tracker.file.domain.FileAccessUrl;
import com.kesi.tracker.user.application.dto.MyProfileResponse;
import com.kesi.tracker.user.application.dto.UserJoinRequest;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import com.kesi.tracker.user.domain.Email;
import com.kesi.tracker.user.domain.Phone;
import com.kesi.tracker.user.domain.Role;
import com.kesi.tracker.user.domain.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static User toDomain(UserJoinRequest dto, String  encodedPassword) {
        return User.builder()
                .email(new Email(dto.getEmail()))
                .password(encodedPassword)
                .name(dto.getName())
                .nickname(dto.getNickname())
                .phone(new Phone(dto.getPhone().replace("-", "").trim()))
                .birthday(dto.getBirthday())
                .createdAt(LocalDateTime.now())
                .role(Role.USER)
                .build();
    }

    public static MyProfileResponse toMyProfileResponse(User user, List<FileAccessUrl> profileImageUrls) {
        return MyProfileResponse.builder()
                .email(user.getEmail().value())
                .name(user.getName())
                .nickname(user.getNickname())
                .phone(user.getPhone() != null ? user.getPhone().value() : null)
                .birthday(user.getBirthday())
                .createdAt(user.getCreatedAt())
                .profileImageUrls(profileImageUrls.stream().map(FileAccessUrl::value).toList())
                .build();
    }

    public static UserProfileResponse toUserProfileResponse(User user, List<FileAccessUrl> profileImageUrls) {
        return UserProfileResponse.builder()
                .email(user.getEmail().value())
                .nickname(user.getNickname())
                .profileImageUrls(profileImageUrls.stream().map(FileAccessUrl::value).toList())
                .build();
    }
}
