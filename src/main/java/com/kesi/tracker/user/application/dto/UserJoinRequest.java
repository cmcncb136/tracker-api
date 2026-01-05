package com.kesi.tracker.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserJoinRequest {
    private String email;
    private String password;
    private String name;
    private String nickname;
    private String phone;
    private List<Long> profileIds;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "Asia/Seoul")
    private LocalDateTime birthday;
}
