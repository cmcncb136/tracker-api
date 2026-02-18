package com.kesi.tracker.user.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "회원 가입 요청")
@Data
public class UserJoinRequest {
    @Schema(description = "이메일", example = "user@example.com")
    private String email;
    @Schema(description = "비밀번호", example = "password123!")
    private String password;
    @Schema(description = "이름", example = "홍길동")
    private String name;
    @Schema(description = "닉네임", example = "길동이")
    private String nickname;
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phone;
    @Schema(description = "프로필 이미지 파일 ID 목록")
    private List<Long> profileIds;

    @Schema(description = "생년월일", example = "1990-01-01")
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd",
            timezone = "Asia/Seoul")
    private LocalDate birthday;
}
