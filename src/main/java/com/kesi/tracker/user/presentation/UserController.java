package com.kesi.tracker.user.presentation;

import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.application.dto.MyProfileResponse;
import com.kesi.tracker.user.application.dto.UserJoinRequest;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User API", description = "사용자 인증 및 프로필 관리")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 가입")
    @PostMapping
    public void join(@RequestBody UserJoinRequest dto) {
        userService.join(dto);
    }

    @Operation(summary = "내 프로필 조회", description = "로그인한 사용자의 프로필 정보를 조회합니다.")
    @GetMapping("/profile")
    public MyProfileResponse getMyProfile(@UserId Long userId) {
        return userService.getMyProfile(userId);
    }

    @Operation(summary = "타인 프로필 조회", description = "UID를 통해 특정 사용자의 공개 프로필 정보를 조회합니다.")
    @GetMapping("/{uid}")
    public UserProfileResponse getUserProfile(
            @PathVariable long uid
    ) {
        return userService.getProfile(uid);
    }
}
