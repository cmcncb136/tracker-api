package com.kesi.tracker.user.application;

import com.kesi.tracker.file.application.FileService;
import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.file.domain.FileOwner;
import com.kesi.tracker.file.domain.FilePurpose;
import com.kesi.tracker.file.domain.OwnerType;
import com.kesi.tracker.user.UserMapper;
import com.kesi.tracker.user.application.dto.MyProfileResponse;
import com.kesi.tracker.user.application.dto.UserJoinRequest;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import com.kesi.tracker.user.application.repository.UserRepository;
import com.kesi.tracker.user.domain.Email;
import com.kesi.tracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileService fileService;

    @Override
    public Optional<User> findByEmail(Email email) {
        return userRepository.findByEmail(email.value());
    }

    @Override
    public User getByEmail(Email email) {
        return userRepository.findByEmail(email.value()).orElseThrow(() -> new RuntimeException("User not found"));    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void join(UserJoinRequest dto) {
        if(userRepository.existsByEmail(dto.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        if(userRepository.existsByPhone(dto.getPhone()))
            throw new IllegalArgumentException("Phone already in use");

        User user = UserMapper.toDomain(dto);
        User savedUser = userRepository.save(user);

        //Profile 파일로 저장 및 Owner 설정
        fileService.assignFileOwner(FileOwner.ofUser(savedUser.getId()), dto.getProfileIds());
    }

    @Override
    public MyProfileResponse getMyProfile(Long id) {
        FileOwner owner = FileOwner.ofUser(id);

        return UserMapper.toMyProfileResponse(this.getById(id),
                fileService.findAccessUrlByOwner(owner));
    }

    @Override
    public UserProfileResponse getProfile(String email) {
        User user = this.getByEmail(new Email(email));
        FileOwner owner = FileOwner.ofUser(user.getId());

        return UserMapper.toUserProfileResponse(user,
                fileService.findAccessUrlByOwner(owner));
    }

    @Override
    public UserProfileResponse getProfile(Long id) {
        User user = this.getById(id);
        FileOwner owner = FileOwner.ofUser(user.getId());

        return UserMapper.toUserProfileResponse(
                user,
                fileService.findAccessUrlByOwner(owner)
        );
    }
}















