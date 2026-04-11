package com.kesi.tracker.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "잘못된 입력 값입니다"),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다"),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "대상을 찾을 수 없습니다"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류입니다"),

    // JWT (추가)
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    TOKEN_SIGNATURE_INVALID(HttpStatus.UNAUTHORIZED, "잘못된 서명입니다"),
    TOKEN_UNSUPPORTED(HttpStatus.UNAUTHORIZED, "지원되지 않는 토큰 형식입니다"),

    // Refresh Token 관련 추가
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "리프레시 토큰을 찾을 수 없습니다. 다시 로그인하세요"),
    REFRESH_TOKEN_MISMATCH(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 일치하지 않습니다"),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    EMAIL_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 사용 중인 이메일입니다"),
    PHONE_DUPLICATION(HttpStatus.BAD_REQUEST, "이미 사용 중인 전화번호입니다"),
    INVALID_EMAIL_FORMAT(HttpStatus.BAD_REQUEST, "이메일 형식이 올바르지 않습니다"),
    INVALID_PHONE_FORMAT(HttpStatus.BAD_REQUEST, "전화번호 형식이 올바르지 않습니다"),
    INVALID_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "비밀번호 길이가 올바르지 않습니다"),

    // Group
    GROUP_NOT_FOUND(HttpStatus.NOT_FOUND, "그룹을 찾을 수 없습니다"),
    GROUP_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "그룹 멤버를 찾을 수 없습니다"),
    NOT_GROUP_LEADER(HttpStatus.FORBIDDEN, "그룹 리더 전용 권한입니다"),
    NOT_GROUP_MEMBER(HttpStatus.FORBIDDEN, "그룹 멤버가 아닙니다"),
    NOT_APPROVED_MEMBER(HttpStatus.FORBIDDEN, "승인된 그룹 멤버가 아닙니다"),
    GROUP_MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 그룹 멤버이거나 초대가 진행 중입니다"),
    MEMBER_COUNT_UNDERFLOW(HttpStatus.BAD_REQUEST, "멤버 수는 0보다 작을 수 없습니다"),
    INVALID_MEMBER_STATUS(HttpStatus.BAD_REQUEST, "해당 상태 변경은 지원하지 않습니다"),
    UNAUTHORIZED_STATUS_CHANGE(HttpStatus.FORBIDDEN, "해당 멤버의 상태를 변경할 권한이 없습니다"),
    INVALID_STATE_TRANSITION(HttpStatus.BAD_REQUEST, "유효하지 않은 상태 전이입니다"),

    // Track
    TRACK_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 트랙을 찾을 수 없습니다"),
    TRACK_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "트랙 멤버를 찾을 수 없습니다"),
    NOT_GROUP_HOST(HttpStatus.FORBIDDEN, "모임 호스트 전용 권한입니다"),
    TRACK_REGISTRATION_CLOSED(HttpStatus.BAD_REQUEST, "수강 신청 기간이 아닙니다"),
    NOT_FOLLOWER(HttpStatus.BAD_REQUEST, "팔로워만 신청이 가능합니다"),
    TRACK_FULL(HttpStatus.BAD_REQUEST, "수강 인원이 모두 찼습니다"),
    CANNOT_CANCEL_TRACK(HttpStatus.BAD_REQUEST, "취소할 수 없는 상태입니다"),
    CANNOT_MODIFY_TRACK(HttpStatus.FORBIDDEN, "해당 트랙을 수정할 수 있는 권한이 없습니다"),
    HOST_CANNOT_CANCEL_TRACK(HttpStatus.BAD_REQUEST, "트랙 생성자는 수강 취소를 할 수 없습니다"),

    // File
    INVALID_FILE_PURPOSE(HttpStatus.BAD_REQUEST, "올바르지 않은 파일 용도입니다"),
    FILE_PATH_NOT_FOUND(HttpStatus.NOT_FOUND, "파일 경로를 찾을 수 없습니다"),

    // Notice
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 공지를 찾을 수 없습니다");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
