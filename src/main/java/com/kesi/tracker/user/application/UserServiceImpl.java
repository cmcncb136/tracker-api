package com.kesi.tracker.user.application;

import com.kesi.tracker.file.application.FileService;
import com.kesi.tracker.file.domain.*;
import com.kesi.tracker.group.application.query.FileOwners;
import com.kesi.tracker.user.UserMapper;
import com.kesi.tracker.user.application.dto.MyProfileResponse;
import com.kesi.tracker.user.application.dto.UserJoinRequest;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import com.kesi.tracker.user.application.repository.UserRepository;
import com.kesi.tracker.user.domain.Email;
import com.kesi.tracker.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final FileService fileService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

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
    public List<User> getByIds(List<Long> ids) {
        Set<Long> idSet = new HashSet<>(ids);

        return this.getByIds(idSet);
    }

    @Override
    public List<User> getByIds(Set<Long> ids) {
        List<User> user =  userRepository.findByIdIn(ids.stream().toList());

        if(user.size() != ids.size())
            throw new RuntimeException("User not found (request : " + ids.size() + ", found : " + user.size() + ")");

        return user;
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

        User user = UserMapper.toDomain(dto, bCryptPasswordEncoder.encode(dto.getPassword()));
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


    @Override
    public Map<Long, UserProfileResponse> getProfiles(Set<Long> ids) {
        List<User> users = this.getByIds(ids);
        Map<Long, List<FileAccessUrl>> accessUrlMap =
                fileService.findAccessUrlByOwners(FileOwners.ofUser(users.stream().map(User::getId).toList()));

        return users.stream().collect(Collectors.toMap(
                User::getId,
                user -> UserMapper.toUserProfileResponse(
                        user,
                        accessUrlMap.getOrDefault(user.getId(), Collections.emptyList()))
        ));
    }
}















