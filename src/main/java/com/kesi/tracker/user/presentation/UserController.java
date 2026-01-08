package com.kesi.tracker.user.presentation;

import com.kesi.tracker.user.application.UserService;
import com.kesi.tracker.user.application.dto.UserJoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping
    public void join(@RequestBody UserJoinRequest dto) {
        userService.join(dto);
    }
}
