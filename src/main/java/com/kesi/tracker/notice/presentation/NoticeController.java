package com.kesi.tracker.notice.presentation;

import com.kesi.tracker.core.security.annotation.UserId;
import com.kesi.tracker.notice.application.NoticeService;
import com.kesi.tracker.notice.application.dto.NoticeCreationRequest;
import com.kesi.tracker.notice.application.dto.NoticeResponse;
import com.kesi.tracker.notice.application.dto.NoticeTitleResponse;
import com.kesi.tracker.notice.application.dto.NoticeUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/groups/{gid}/notices")
    public NoticeResponse create(
            @PathVariable long gid,
            @RequestBody NoticeCreationRequest noticeCreationRequest,
            @UserId long userId) {

        return noticeService.create(noticeCreationRequest, gid, userId);
    }

    @GetMapping("/groups/{gid}/notices")
    public Page<NoticeTitleResponse> search(
            @PathVariable long gid,
            @RequestParam String keyword,
            @UserId long userId,
            @PageableDefault Pageable pageable
    ) {
        return noticeService.search(gid, keyword, userId, pageable);
    }

    @GetMapping("/notices/{noticeId}")
    public NoticeResponse get(
            @PathVariable long noticeId,
            @UserId long userId
    ) {
        return noticeService.getById(noticeId, userId);
    }

    @PatchMapping("notices/{noticeId}")
    public NoticeResponse update(
            @RequestParam NoticeUpdateRequest request,
            @UserId long userId
    ) {
        return noticeService.update(request, userId);
    }
}
