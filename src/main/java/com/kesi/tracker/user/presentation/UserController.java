package com.kesi.tracker.user.presentation;

import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.application.dto.MyProfileResponse;
import com.kesi.tracker.user.application.dto.UserJoinRequest;
import com.kesi.tracker.user.application.dto.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void join(@RequestBody UserJoinRequest dto) {
        userService.join(dto);
    }

    @GetMapping("profile")
    public MyProfileResponse getMyProfile() {
        //Todo. 추후 id 정보를 가져올 예정 아직 기술 미지정
        return null;
    }

    @GetMapping("/{uid}")
    public UserProfileResponse getUserProfile(
            @PathVariable long uid
    ) {
        return null;
    }
}
