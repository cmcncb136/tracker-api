package com.kesi.tracker.user.application;

import com.kesi.tracker.file.application.FileService;
import com.kesi.tracker.file.domain.File;
import com.kesi.tracker.user.UserMapper;
import com.kesi.tracker.user.application.dto.UserJoinRequest;
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
        userRepository.save(user);

        List<File> files = fileService.findByIds(dto.getProfileIds());
        files.forEach(file -> {
            file
        });
    }
}















