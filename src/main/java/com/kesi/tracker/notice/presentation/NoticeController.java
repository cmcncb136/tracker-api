package com.kesi.tracker.notice.presentation;

import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.notice.application.NoticeService;
import com.kesi.tracker.notice.application.dto.NoticeCreationRequest;
import com.kesi.tracker.notice.application.dto.NoticeResponse;
import com.kesi.tracker.notice.application.dto.NoticeTitleResponse;
import com.kesi.tracker.notice.application.dto.NoticeUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Notice API", description = "그룹 공지사항 관리")
@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "공지사항 작성")
    @PostMapping("/groups/{gid}/notices")
    public NoticeResponse create(
            @PathVariable long gid,
            @RequestBody NoticeCreationRequest noticeCreationRequest,
            @UserId long userId) {

        return noticeService.create(noticeCreationRequest, gid, userId);
    }

    @Operation(summary = "공지사항 목록 조회/검색", description = "그룹 내 공지사항을 제목 또는 내용 키워드로 검색합니다.")
    @GetMapping("/groups/{gid}/notices")
    public Page<NoticeTitleResponse> search(
            @PathVariable long gid,
            @RequestParam String keyword,
            @UserId long userId,
            @PageableDefault Pageable pageable
    ) {
        return noticeService.search(gid, keyword, userId, pageable);
    }

    @Operation(summary = "공지사항 상세 조회")
    @GetMapping("/notices/{noticeId}")
    public NoticeResponse get(
            @PathVariable long noticeId,
            @UserId long userId
    ) {
        return noticeService.getById(noticeId, userId);
    }

    @Operation(summary = "공지사항 수정")
    @PatchMapping("notices/{noticeId}")
    public NoticeResponse update(
            @RequestParam NoticeUpdateRequest request,
            @UserId long userId
    ) {
        return noticeService.update(request, userId);
    }
}
